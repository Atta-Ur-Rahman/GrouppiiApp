package com.techease.groupiiapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techease.groupiiapplication.R;

public class AnimationRVUtill {

    public static LayoutAnimationController RecylerViewAnimation(Context context) {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_right_to_left);

        return controller;
    }


    public static void animateFade(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_fade_enter, R.anim.animate_fade_exit);
    }


    public static void animateZoom(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_zoom_enter, R.anim.animate_zoom_exit);
    }

}
