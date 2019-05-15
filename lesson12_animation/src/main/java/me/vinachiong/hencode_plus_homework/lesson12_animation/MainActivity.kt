package me.vinachiong.hencode_plus_homework.lesson12_animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import me.vinachiong.hencode_plus_homework.lesson12_animation.view.CameraView
import me.vinachiong.hencode_plus_homework.lesson12_animation.view.CircleView
import me.vinachiong.lib.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.circle_view)
        if (view is CircleView) {
            startAnimationForCircleView(view)
        } else if (view is CameraView) {
            startAnimationForCameraView(view)
        }
    }

    private fun startAnimationForCircleView(view: CircleView) {

        val animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(2f), Utils.dp2px(180f))
        animator.duration = 800L
        animator.startDelay = 100L
        animator.start()
    }

    private fun startAnimationForCameraView(view: CameraView) {
//        val topFlipAnimator = ObjectAnimator.ofFloat(circleView, "topFlip", 0f, 30f)
//        val bottomFlipAnimator = ObjectAnimator.ofFloat(circleView, "bottomFlip", 0f, -30f)
//        val flipRotationAnimator = ObjectAnimator.ofFloat(circleView, "flipRotation", 0f, 270f)
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playSequentially(listOf(bottomFlipAnimator, topFlipAnimator, flipRotationAnimator))
//        animatorSet.startDelay = 1000L
//        animatorSet.duration = 1000L
//        animatorSet.start()
//        val rotateAnimator = ObjectAnimator.ofFloat(view, "rotate", 30f, 390f)
//
//        val set = AnimatorSet()
//        set.play(rotateAnimator)
//        set.startDelay = 1000L
//        set.duration = 9000L
//        set.start()
    }
}
