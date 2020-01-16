package com.example.mafia.animations;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.AccelerateInterpolator;

import com.example.mafia.R;
import com.example.mafia.models.GameModel;
import com.google.android.material.card.MaterialCardView;

public class AnimationUtilsHelper {
    public static final String TAG = AnimationUtilsHelper.class.getSimpleName();
    public static final int CAMERA_DISTANCE = 4000;
    public static final int ANGLE_START = 0;
    public static final int ANGLE_FINISH = 180;
    public static final int ANGLE_SHOWING_BACK = 85;
    private Context mContext;
    private MaterialCardView mTarget;
    private ValueAnimator mAnimator;
    private AnimatorListenerAdapter mIsDone;

    public AnimationUtilsHelper(Context context) {
        mContext = context;
    }

    public AnimationUtilsHelper animationRole(GameModel model, MaterialCardView target) {
        mTarget = target;
        model.setShowCardRole(true);
        float scale = mContext.getResources().getDisplayMetrics().density;
        mTarget.setCameraDistance(CAMERA_DISTANCE * scale);
        mAnimator = ValueAnimator.ofFloat(ANGLE_START, ANGLE_FINISH);
        mAnimator.addUpdateListener(animation -> {
            Float angle = (Float) animation.getAnimatedValue();
            if (angle >= ANGLE_SHOWING_BACK && !model.getBackImage()) {
                model.setBackImage(true);
                model.setIsShowRole(true);
            }
            mTarget.setRotationY(angle);
            mTarget.requestLayout();
        });
        mAnimator.setDuration(mContext.getResources().getInteger(R.integer.flip_duration));
        mAnimator.setInterpolator(new AccelerateInterpolator());
        return this;
    }

    public void start() {
        mAnimator.start();
    }

    public void stop() {
        mAnimator.cancel();
    }

    public void addListener(AnimatorListenerAdapter listener) {
        mIsDone = listener;
        mIsDone.onAnimationEnd(mAnimator);

    }

}
