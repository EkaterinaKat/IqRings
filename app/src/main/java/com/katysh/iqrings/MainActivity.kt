package com.katysh.iqrings

import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.katysh.iqrings.coreadapter.Exercise
import com.katysh.iqrings.view.GameBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val root = findViewById<RelativeLayout>(R.id.root)
        GameBuilder(this, root).startGame()
    }




}