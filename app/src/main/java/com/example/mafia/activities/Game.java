package com.example.mafia.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.animations.AnimationUtilsHelper;
import com.example.mafia.databinding.GameBinding;
import com.example.mafia.fragments.Dialog_Freeplace;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.ChatAdapter;
import com.example.mafia.models.GameModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.adapters.RolesAdapter;
import com.example.mafia.utils.FabricDialogs;
import com.example.mafia.utils.SettingsUtils;
import com.example.mafia.utils.TimerGame;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.atomic.AtomicInteger;

import static com.example.mafia.utils.Logger.PTAG;

public class Game extends AppCompatActivity implements OnFinished {
    public static final String TAG = Game.class.getSimpleName();

    public static final int START = 1;
    public static final int STOP = 2;
    public static final int RESUME = 3;
    public static final int RESET = 0;

    private GameModel mModel;
    private MaterialCardView mMyRole;
    private RecyclerView mShowMessages;
    private EditText mEditLineMessage;
    private MaterialButton mSendMessage;
    private GameBinding mBinding;
    private RolesAdapter mAdapter;
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

        mTime.setOnClickListener((c) -> mModel.resetRoles());
        mModel.getMessages().observe(this, chatModels -> {
            ChatAdapter mChat = new ChatAdapter(chatModels, this);
            mShowMessages.setAdapter(mChat);
            mShowMessages.smoothScrollToPosition(mChat.getItemCount());
        });


        mGetRole = mModel.getRole();
        mGetRole.observe(this, (role) -> {
            if (!role.getRoom().equals(getResources().getString(R.string.no_free_rooms))){
                mFrame = getSupportFragmentManager().beginTransaction().hide(mFragment);
                mFrame.commit();
                mModel.setActor(role.getRole().getRoleName());
                mModel.setIdImage(role.getRole().getRoleDrawable());
                mAnimation.animationRole(mModel, mMyRole).start();
                mAnimation.addListener(changeAnimation);
                Log.d(PTAG, TAG + "@onCreate: get players 1");
                mModel.getPlayers(role.getRoom()).observe(this, roleModels -> {
                    Log.d(PTAG, TAG + "@onCreate: get players 2");
                    mAdapter = new RolesAdapter(Game.this, roleModels,role);
                    mRecyclerRols.setAdapter(mAdapter);
                });
            } else {
                mFrame = getSupportFragmentManager().beginTransaction().hide(mFragment);
                mFrame.commitNow();
                Toast.makeText(getBaseContext(),"Свободных игроков нет",Toast.LENGTH_LONG).show();
            }
        });


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
        mTimeGame = new TimerGame(mTime, this);
        mTimeGame.resume();
        mMyRole = findViewById(R.id.card_role);
        mRecyclerRols = findViewById(R.id.roleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerRols.setLayoutManager(layoutManager);
        mShowMessages = findViewById(R.id.show_messages);
        mShowMessages.setLayoutManager(new LinearLayoutManager(this));
        mEditLineMessage = findViewById(R.id.edit_line_message);
        mSendMessage = findViewById(R.id.send_message);
        mAnimation = new AnimationUtilsHelper(this);
    }

    public void dialog_loader() {
        mFragment = mModel.getDialog(FabricDialogs.CODE_DIALOG_LOADER);
        mFrame = getSupportFragmentManager().beginTransaction();
        mFrame.replace(R.id.ShowDialog, mFragment).commit();
    }

    private AtomicInteger test = new AtomicInteger(100);
    private void leavePlayers() {
        mMyRole.setVisibility(View.GONE);
        Toast.makeText(getBaseContext(), mModel.getActor(), Toast.LENGTH_LONG).show();
        mModel.getFreePlace(mGetRole.getValue().getRoom()).observe(this, integer -> {
            if (test.get() > integer) {
                if (integer == 0) {
                    Log.d(PTAG, TAG + "@leavePlayers: haven't free places");
                    mFrame = getSupportFragmentManager().beginTransaction();
                    mFrame.replace(R.id.ShowDialog, mModel.getDialog(FabricDialogs.CODE_DIALOG_SHOW_RULES)).commit();
                    mModel.startGame().observe(this,aBoolean -> {
                        if (aBoolean)
                            timeGameController(START,TimerGame.TIME_WAITING);
                    });
                } else {
                    Log.d(PTAG, TAG + "@leavePlayers: leave free places = " + integer);
                    mFragment = mModel.getDialog(FabricDialogs.CODE_DIALOG_FREEPLACE);
                    mFrame = getSupportFragmentManager().beginTransaction();
                    mFrame.replace(R.id.ShowDialog, mFragment).commitNow();
                    ((TextView) mFragment.getView().findViewById(R.id.free_count)).setText(String.format(getString(R.string.count_free_place), integer));
                }
            }
        });
    }

    public void timeGameController(Integer code, @Nullable Long setting) {
        mTimeGame.finalCountDown(this);
        switch (code) {
            case START: {
                mTimeGame.start(setting);
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
            case RESUME: {
                mTimeGame.resume();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTimeGame.stop();
        SettingsUtils.getInstance().setTimeBase(mTimeGame.getmChronometer().getBase());
       // mFrame = null;
        mHandler.removeCallbacks(null);
        changeAnimation = null;
    }

    @Override
    public void isFinish(Boolean bool, Integer code) {
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
                mHandler.postDelayed(() -> leavePlayers(), getResources().getInteger(R.integer.flip_duration_half));
            }).start();

        }
    };
}
