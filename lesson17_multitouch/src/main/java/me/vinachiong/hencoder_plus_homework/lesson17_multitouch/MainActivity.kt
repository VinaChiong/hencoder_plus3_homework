package me.vinachiong.hencoder_plus_homework.lesson17_multitouch

import android.os.Bundle
import android.os.Debug
import android.support.v7.app.AppCompatActivity
import me.vinachiong.lib.TraceTool

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TraceTool.startTrace()
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        TraceTool.stopTrace()
        super.onDestroy()
    }
}