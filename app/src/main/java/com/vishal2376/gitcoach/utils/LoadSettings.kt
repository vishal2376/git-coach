package com.vishal2376.gitcoach.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.vishal2376.gitcoach.R

object LoadSettings {

    fun loadTheme(context: Context) {
        //load saved values
        val sp =
            context.getSharedPreferences("SETTINGS", MODE_PRIVATE).getString("user_theme", "blue")

        when (sp.toString()) {
            "red" -> {
                context.setTheme(R.style.Theme_RED)
            }

            "yellow" -> {
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

    fun getFontSize(context: Context, category: String): Float {
        return context.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            .getFloat(category, 0f)
    }

    fun getLocale(context: Context): String {
        return context.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            .getString(Constants.LOCALE, "en").toString()
    }
}