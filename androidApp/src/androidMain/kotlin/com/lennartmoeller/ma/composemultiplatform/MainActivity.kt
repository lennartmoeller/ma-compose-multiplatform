package com.lennartmoeller.ma.composemultiplatform

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // allow content to leak in system bars
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            MainView()
        }
    }
}