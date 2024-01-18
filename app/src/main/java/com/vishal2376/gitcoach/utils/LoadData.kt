package com.vishal2376.gitcoach.utils

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.vishal2376.gitcoach.models.GitCommand
import com.vishal2376.gitcoach.models.lesson.GitLesson
import com.vishal2376.gitcoach.models.quiz.GitQuiz

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

    fun getGitQuizData(context: Context): GitQuiz? {
//        val locale = LoadSettings.getLocale(context)
        val locale = "en" // todo: remove it after translations
        val jsonString = context.assets.readFile("$locale/git_quiz.json")
        return Gson().fromJson(jsonString, GitQuiz::class.java)
    }

    private fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }
}