package com.vishal2376.gitcoach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vishal2376.gitcoach.utils.LoadSettings

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load settings
        LoadSettings.loadTheme(this)

        setContentView(R.layout.activity_main)

    }

}