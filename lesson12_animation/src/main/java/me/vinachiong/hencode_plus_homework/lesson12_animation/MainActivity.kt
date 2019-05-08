package me.vinachiong.hencode_plus_homework.lesson12_animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.vinachiong.hencode_plus_homework.lesson12_animation.view.CameraView
import me.vinachiong.lib.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circleView = findViewById<CameraView>(R.id.camera_view)

        val topFlipAnimator = ObjectAnimator.ofFloat(circleView, "topFlip", 0f, 30f)
        val bottomFlipAnimator = ObjectAnimator.ofFloat(circleView, "bottomFlip", 0f, -30f)
        val flipRotationAnimator = ObjectAnimator.ofFloat(circleView, "flipRotation", 0f, 270f)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(listOf(bottomFlipAnimator, topFlipAnimator, flipRotationAnimator))
        animatorSet.startDelay = 1000L
        animatorSet.duration = 1000L
        animatorSet.start()

    }
}
