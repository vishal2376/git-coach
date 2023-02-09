package com.vishal2376.gitcoach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load settings
        loadSettings()

        setContentView(R.layout.activity_main)

    }

    private fun loadSettings() {
        //load saved values
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val userTheme = sp.getString("user_theme", "yellow")

        //set theme
        setUserTheme(userTheme)
    }

    private fun setUserTheme(userTheme: String?) {
        when (userTheme) {
            "red" -> {
                setTheme(R.style.Theme_RED)
            }
            "blue" -> {
                setTheme(R.style.Theme_BLUE)
            }
            "green" -> {
                setTheme(R.style.Theme_GREEN)
            }
            else -> {
                setTheme(R.style.Theme_GitCoach)
            }
        }
    }
}