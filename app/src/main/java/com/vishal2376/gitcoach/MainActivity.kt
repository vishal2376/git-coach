package com.vishal2376.gitcoach

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.vishal2376.gitcoach.models.GitCommand

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //temp
        val gson = Gson()
        val jsonString = assets.readFile("git_commands.json")
        val gitCommandList = gson.fromJson(jsonString, GitCommand::class.java)

        Log.e("@@@", gitCommandList.toString())

    }

    private fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }
}