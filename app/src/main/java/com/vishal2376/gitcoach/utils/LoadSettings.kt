package com.vishal2376.gitcoach.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.vishal2376.gitcoach.R

object LoadSettings {

    fun checkNotification(context: Context): Int {
        val switch = context.getSharedPreferences("NOTIFICATION", MODE_PRIVATE)
            .getInt("daily_notification", 0)

        if (switch == 1) {
            scheduleNotification(context)
        }

        return switch
    }

    fun loadTheme(context: Context) {
        //load saved values
        val sp =
            context.getSharedPreferences("SETTINGS", MODE_PRIVATE).getString("user_theme", "yellow")

        when (sp.toString()) {
            "red" -> {
                context.setTheme(R.style.Theme_RED)
            }
            "blue" -> {
                context.setTheme(R.style.Theme_YELLOW)
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