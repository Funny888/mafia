package com.example.mafia.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.DialogCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.databinding.GameBinding;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.BD;
import com.example.mafia.models.GameModel;
import com.example.mafia.models.RoleModel;
import com.example.mafia.models.RolesRecycler;
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
    private BD bd;

    public static final String TAG = Game.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);
        setUp();
        mBinding.setModel(mModel);
        bd.getRoom();
        mGetRole = bd.getRole();
        mFreePlace = bd.getFreePlace();
        mGetRole.observe(this, (role) -> {
            mModel.setActor(role.getRoleName());
            mModel.showDialogLoader(false);
            mModel.showDialogFreePlaces(true,mFreePlace.getValue());
            Toast.makeText(getBaseContext(),mModel.getActor(),Toast.LENGTH_LONG).show();
        });
       mFreePlace.observe(this, new Observer<Integer>() {
           @Override
           public void onChanged(Integer integer) {
               if (integer.equals(0))
                   mModel.showDialogFreePlaces(false,integer);
           }
       });

        mRecyclerView.setAdapter(mAdapter);
        //runRole();

    }

    private void setUp(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.game);
        mModel = new GameModel(this);
        mModel.showDialogLoader(true);
        bd = BD.getInstance(this);
        mTime = findViewById(R.id.time_the_game);
        mImageRole = findViewById(R.id.imageRole);
        mMyRole = findViewById(R.id.materialCardView);
        mRole = findViewById(R.id.role);
        mRecyclerView = findViewById(R.id.roleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RolesRecycler(this,bd.getPlayers());
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
                        AnimatorSet animatorStart = (AnimatorSet) AnimatorInflater.loadAnimator(Game.this, R.animator.flip_card_start);
                        AnimatorSet animatorStop = (AnimatorSet) AnimatorInflater.loadAnimator(Game.this, R.animator.flip_card_stop);

                        animatorStart.setTarget(mMyRole);
                        animatorStop.setTarget(mMyRole);
                        animatorStart.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation, boolean isReverse) {

                                mImageRole.setImageDrawable(mGetRole.getValue().getRoleDrawable());
                            }
                        });
                        animatorStop.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mBinding.getModel().setIsShowRole(true);
                                mMyRole.postDelayed(() -> mMyRole.setVisibility(View.GONE),2000);
                            }
                        });
                        animatorStop.setStartDelay(1500);
                        animatorStop.start();
                        animatorStart.setStartDelay(1000);
                        animatorStart.start();
                    }
                });
            }
        }, 4000);
    }



    public void timeGameCintroller(Integer code){
        TimerGame tGame = TimerGame.getInstance(mTime,this);
        tGame.finalCountDown(this);
        switch (code){
            case START:{
                tGame.start();
                break;
            }
            case STOP:{
                tGame.stop();
                break;
            }
            case RESET:{
                tGame.reset();
                break;
            }
        }
    }


    @Override
    public void isFinish(Boolean bool) {
        mModel.setIsShowPlayers(bool);
    }
}
