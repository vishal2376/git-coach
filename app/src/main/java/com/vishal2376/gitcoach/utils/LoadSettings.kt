package com.vishal2376.gitcoach.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.vishal2376.gitcoach.R

object LoadSettings {

    fun loadTheme(context: Context) {
        //load saved values
        val sp = PreferenceManager.getDefaultSharedPreferences(context)

        when (sp.getString("user_theme", "yellow")) {
            "red" -> {
                context.setTheme(R.style.Theme_RED)
            }
            "blue" -> {
                context.setTheme(R.style.Theme_BLUE)
            }
            "green" -> {
                context.setTheme(R.style.Theme_GREEN)
            }
            else -> {
                context.setTheme(R.style.Theme_GitCoach)
            }
        }
    }
}