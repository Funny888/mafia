package com.example.mafia.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.animations.AnimationUtils;
import com.example.mafia.databinding.GameBinding;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.ChatAdapter;
import com.example.mafia.models.GameModel;
import com.example.mafia.models.RoleModel;
import com.example.mafia.models.RolesRecycler;
import com.example.mafia.utils.FabricDialogs;
import com.example.mafia.utils.SettingsUtils;
import com.example.mafia.utils.TimerGame;
import com.google.android.material.card.MaterialCardView;

public class Game extends AppCompatActivity implements OnFinished {
    public static final String TAG = Game.class.getSimpleName();

    public static final int START = 1;
    public static final int STOP = 2;
    public static final int RESET = 0;

    private GameModel mModel;
    private MaterialCardView mMyRole;
    private RecyclerView mShowMessages;
    private EditText mSendMessage;
    private GameBinding mBinding;
    private RolesRecycler mAdapter;
    private RecyclerView mRecyclerRols;
    private LiveData<RoleModel> mGetRole;
    private Chronometer mTime;
    private TimerGame mTimeGame;
    private FragmentTransaction mFrame;
    private AnimationUtils mAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);
        setUp();
        mBinding.setModel(mModel);
        mModel.getRoom();
        mModel.getFreePlace();

//        mModel.resetRoles();
        mModel.getMessages().observe(this,chatModels -> {
            ChatAdapter mChat = new ChatAdapter(chatModels,this);
            mShowMessages.setAdapter(mChat);

        });



        mGetRole = mModel.getRole();
        mGetRole.observe(this, (role) -> {
            mModel.setActor(role.getRoleName());
            mModel.setIdImage(role.getRoleDrawable());
            mAnimation.animationRole(mModel,mMyRole);
            //TODO run mFrame after end animation
            mFrame = getSupportFragmentManager().beginTransaction();
            mFrame.replace(R.id.ShowDialog, mModel.getDialog(FabricDialogs.CODE_DIALOG_SHOW_RULES)).commit();
            Toast.makeText(getBaseContext(),mModel.getActor(),Toast.LENGTH_LONG).show();
        });
        mModel.getFreePlace().observe(this,integer -> {
            if (integer == 0) {
                findViewById(R.id.ShowDialog).setVisibility(View.GONE);
            }
        });
        mRecyclerRols.setAdapter(mAdapter);

    }


    private void setUp(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.game);
        mModel = new GameModel(this);


        dialog_loader();
        mTime = findViewById(R.id.time_the_game);
        mMyRole = findViewById(R.id.card_role);
        mRecyclerRols = findViewById(R.id.roleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerRols.setLayoutManager(layoutManager);
        mAdapter = new RolesRecycler(this,mModel.getPlayers());
        mShowMessages = findViewById(R.id.show_messages);
        mShowMessages.setLayoutManager(new LinearLayoutManager(this));
        mSendMessage = findViewById(R.id.send_message);
        mAnimation = new AnimationUtils(this);
        timeGameCintroller(START);
    }

    public void dialog_loader(){
        mFrame = getSupportFragmentManager().beginTransaction();
        mFrame.replace(R.id.ShowDialog,mModel.getDialog(FabricDialogs.CODE_DIALOG_LOADER)).commit();
    }

    public void timeGameCintroller(Integer code){
        mTimeGame =  new TimerGame(mTime,this);
        mTimeGame.finalCountDown(this);
        switch (code){
            case START:{
                mTimeGame.start();
                break;
            }
            case STOP:{
                mTimeGame.stop();
                break;
            }
            case RESET:{
                mTimeGame.reset();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeGame.stop();
        SettingsUtils.getInstance().setTimeBase(mTimeGame.getmChronometer().getBase());
    }

    @Override
    public void isFinish(Boolean bool) {
        mModel.setIsShowPlayers(bool);
    }
}
