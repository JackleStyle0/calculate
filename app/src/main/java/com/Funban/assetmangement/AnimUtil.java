package com.Funban.assetmangement;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

/**
 * Created by spaky on 23/7/2559.
 */
public class AnimUtil {
    public static void startAnim(Context ctx, View view, int resAnim, final AnimateFinishCallback callback) {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(ctx, resAnim);
        animator.setTarget(view);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (callback != null) {
                    callback.onAnimateFinished();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        view.setVisibility(View.VISIBLE);
    }


    public interface AnimateFinishCallback {
        void onAnimateFinished();
    }
}
