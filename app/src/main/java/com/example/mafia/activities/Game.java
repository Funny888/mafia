package com.example.mafia.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.databinding.GameBinding;
import com.example.mafia.fragments.Dialog_Freeplace;
import com.example.mafia.fragments.Dialog_Loader;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.GameModel;
import com.example.mafia.models.RoleModel;
import com.example.mafia.models.RolesRecycler;
import com.example.mafia.utils.SettingsUtils;
import com.example.mafia.utils.TimerGame;
import com.google.android.material.card.MaterialCardView;

public class Game extends AppCompatActivity implements OnFinished {
    public static final int START = 1;
    public static final int STOP = 2;
    public static final int RESET = 0;

    private GameModel mModel;
    private TextView mRole;
    private ImageView mImageRole;
    private MaterialCardView mMyRole;
    private GameBinding mBinding;
    private RolesRecycler mAdapter;
    private RecyclerView mRecyclerView;
    private LiveData<RoleModel> mGetRole;
    private LiveData<Integer> mFreePlace;
    private Chronometer mTime;
    private Dialog_Loader dialog_loader;
    private TimerGame mTimeGame;
    private FragmentTransaction mFrame;

    public static final String TAG = Game.class.getSimpleName();

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


        mGetRole = mModel.getRole();
        mGetRole.observe(this, (role) -> {
            mModel.setActor(role.getRoleName());
            mFrame = getSupportFragmentManager().beginTransaction();
            mFrame.replace(R.id.ShowDialog, mModel.getDialog(2)).commit();
            Toast.makeText(getBaseContext(),mModel.getActor(),Toast.LENGTH_LONG).show();
        });
        mModel.getFreePlace().observe(this,integer -> {
            if (integer == 0) {
                findViewById(R.id.ShowDialog).setVisibility(View.GONE);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
     //   runRole();

    }


    private void setUp(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.game);
        mModel = new GameModel(this);


        dialog_loader();
        mTime = findViewById(R.id.time_the_game);
        mImageRole = findViewById(R.id.imageRole);
        mMyRole = findViewById(R.id.materialCardView);
        mRole = findViewById(R.id.role);
        mRecyclerView = findViewById(R.id.roleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RolesRecycler(this,mModel.getPlayers());
        timeGameCintroller(START);


    }

    private void runRole() {
        mMyRole.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMyRole.setVisibility(View.VISIBLE);
                        float scale = getBaseContext().getResources().getDisplayMetrics().density;
                        mMyRole.setCameraDistance(4000 * scale);


                        AnimatorSet animatorStart = (AnimatorSet) AnimatorInflater.loadAnimator(Game.this, R.animator.flip_card_start);
                        AnimatorSet animatorStop = (AnimatorSet) AnimatorInflater.loadAnimator(Game.this, R.animator.flip_card_stop);

                        animatorStart.setTarget(mMyRole);
                        animatorStop.setTarget(mMyRole);
                        animatorStart.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation, boolean isReverse) {

                                mImageRole.setImageDrawable(getDrawable(R.drawable.card_image_people));
                            }
                        });
                        animatorStop.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mBinding.getModel().setIsShowRole(true);
                                mMyRole.postDelayed(() -> mMyRole.setVisibility(View.GONE),2000);
                            }
                        });
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(animatorStart,animatorStop);
                        set.start();
                    }
                });
            }
        }, 4000);
    }


    public void dialog_loader(){
        mFrame = getSupportFragmentManager().beginTransaction();
                mFrame.replace(R.id.ShowDialog,mModel.getDialog(1)).commit();
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
