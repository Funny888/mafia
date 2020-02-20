package com.example.mafia.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.animations.AnimationUtilsHelper;
import com.example.mafia.databinding.GameBinding;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.ChatAdapter;
import com.example.mafia.models.GameModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.models.RoleModel;
import com.example.mafia.models.RolesRecycler;
import com.example.mafia.utils.FabricDialogs;
import com.example.mafia.utils.SettingsUtils;
import com.example.mafia.utils.TimerGame;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Game extends AppCompatActivity implements OnFinished {
    public static final String TAG = Game.class.getSimpleName();

    public static final int START = 1;
    public static final int STOP = 2;
    public static final int RESET = 0;

    private GameModel mModel;
    private MaterialCardView mMyRole;
    private RecyclerView mShowMessages;
    private EditText mEditLineMessage;
    private MaterialButton mSendMessage;
    private GameBinding mBinding;
    private RolesRecycler mAdapter;
    private RecyclerView mRecyclerRols;
    private LiveData<GamePlace> mGetRole;
    private Chronometer mTime;
    private TimerGame mTimeGame;
    private FragmentTransaction mFrame;
    private AnimationUtilsHelper mAnimation;
    private Fragment mFragment;
    private Handler mHandler = new Handler(Looper.myLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);
        setUp();
        mBinding.setModel(mModel);
        //mModel.getRoom();
        mModel.getFreePlace();

        mTime.setOnClickListener((c) -> mModel.resetRoles());
        mModel.getMessages().observe(this, chatModels -> {
            ChatAdapter mChat = new ChatAdapter(chatModels, this);
            mShowMessages.setAdapter(mChat);
            mShowMessages.smoothScrollToPosition(mChat.getItemCount());
        });


        mGetRole = mModel.getRole();
        mGetRole.observe(this, (role) -> {
            mFrame = getSupportFragmentManager().beginTransaction().hide(mFragment);
            mFrame.commit();
            mModel.setActor(role.getRole().getRoleName());
            mModel.setIdImage(role.getRole().getRoleDrawable());
            mAnimation.animationRole(mModel, mMyRole).start();
            mAnimation.addListener(changeAnimation);
        });

        mModel.getFreePlace().observe(this, integer -> {
            if (integer == 0) {
                findViewById(R.id.ShowDialog).setVisibility(View.GONE);
            }
        });
        mRecyclerRols.setAdapter(mAdapter);

        mSendMessage.setOnClickListener((c) -> {
            mModel.sendMessages(mEditLineMessage.getText().toString());
            mEditLineMessage.setText("");
        });
    }


    private void setUp() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.game);
        mBinding.setLifecycleOwner(this);
        mModel = ViewModelProviders.of(this).get(GameModel.class);


        dialog_loader();
        mTime = findViewById(R.id.time_the_game);
        mMyRole = findViewById(R.id.card_role);
        mRecyclerRols = findViewById(R.id.roleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerRols.setLayoutManager(layoutManager);
        mAdapter = new RolesRecycler(this, mModel.getPlayers());
        mShowMessages = findViewById(R.id.show_messages);
        mShowMessages.setLayoutManager(new LinearLayoutManager(this));
        mEditLineMessage = findViewById(R.id.edit_line_message);
        mSendMessage = findViewById(R.id.send_message);
        mAnimation = new AnimationUtilsHelper(this);
        timeGameCintroller(START);
    }

    public void dialog_loader() {
        mFragment = mModel.getDialog(FabricDialogs.CODE_DIALOG_LOADER);
        mFrame = getSupportFragmentManager().beginTransaction();
        mFrame.replace(R.id.ShowDialog, mFragment).commit();
    }

    @MainThread
    private void leavePalyers() {
        mMyRole.setVisibility(View.GONE);
        mFrame = getSupportFragmentManager().beginTransaction();
        mFrame.replace(R.id.ShowDialog, mModel.getDialog(FabricDialogs.CODE_DIALOG_SHOW_RULES)).commit();
        Toast.makeText(getBaseContext(), mModel.getActor(), Toast.LENGTH_LONG).show();
    }

    public void timeGameCintroller(Integer code) {
        mTimeGame = new TimerGame(mTime, this);
        mTimeGame.finalCountDown(this);
        switch (code) {
            case START: {
                mTimeGame.start();
                break;
            }
            case STOP: {
                mTimeGame.stop();
                break;
            }
            case RESET: {
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

    AnimatorListenerAdapter changeAnimation = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            new Thread(() -> {
                while (true) {
                    if (!animation.isRunning()) break;
                }
                mHandler.postDelayed(() -> leavePalyers(), getResources().getInteger(R.integer.flip_duration_half));
            }).start();

        }
    };
}
