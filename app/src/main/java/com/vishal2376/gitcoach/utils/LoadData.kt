package com.vishal2376.gitcoach.utils

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.vishal2376.gitcoach.models.GitCommand
import com.vishal2376.gitcoach.models.lesson.GitLesson

object LoadData {

    fun getGitCommandData(context: Context): GitCommand? {
        val locale = LoadSettings.getLocale(context)
        val jsonString = context.assets.readFile("$locale/git_commands.json")
        return Gson().fromJson(jsonString, GitCommand::class.java)
    }

    fun getGitLessonData(context: Context): GitLesson? {
        val locale = LoadSettings.getLocale(context)
        val jsonString = context.assets.readFile("$locale/git_lessons.json")
        return Gson().fromJson(jsonString, GitLesson::class.java)
    }

    private fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }
}