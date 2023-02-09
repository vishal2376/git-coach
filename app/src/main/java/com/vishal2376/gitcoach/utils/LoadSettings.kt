package com.vishal2376.gitcoach.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.vishal2376.gitcoach.R

object LoadSettings {

    fun loadTheme(context: Context) {
        //load saved values
//        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val sp = context.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            .getString("user_theme", "yellow")

        when (sp.toString()) {
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