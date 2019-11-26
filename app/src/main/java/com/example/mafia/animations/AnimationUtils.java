package com.example.mafia.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import com.example.mafia.R;
import com.example.mafia.models.GameModel;
import com.google.android.material.card.MaterialCardView;

public class AnimationUtils {
    private Context mContext;

    public AnimationUtils(Context context){
        mContext = context;
    }

    public void animationRole(GameModel model, MaterialCardView target){
        target.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.setShowCardRole(true);
                float scale = mContext.getResources().getDisplayMetrics().density;
                target.setCameraDistance(4000 * scale);


                AnimatorSet animatorStart = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.flip_card_start);
                AnimatorSet animatorStop = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.flip_card_stop);

                animatorStart.setTarget(target);
                animatorStop.setTarget(target);
                animatorStart.addListener(new AnimatorListenerAdapter(){
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        model.setBackImage(true);
                    }
                } );

                animatorStop.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        model.setIsShowRole(true);
                        target.postDelayed(() -> target.setVisibility(View.GONE),2000);
                    }
                });
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animatorStart,animatorStop);
                set.start();
            }
        },4000);

    }
}
