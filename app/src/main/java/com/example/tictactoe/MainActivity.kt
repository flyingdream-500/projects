package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var level = 1_000
        //iv_robot.setImageLevel(level)

        iv_robot.setOnClickListener {
            iv_robot.setImageLevel(level)
            level += 1_000
        }
    }
}