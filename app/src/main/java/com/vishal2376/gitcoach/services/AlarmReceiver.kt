package com.vishal2376.gitcoach.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadData
import com.vishal2376.gitcoach.utils.LoadSettings
import com.vishal2376.gitcoach.utils.ReminderManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendRandomNotification(context)

        //Schedule Next Notification
        val notificationTime = LoadSettings.getNotificationTime(context)
        ReminderManager.startReminder(context, notificationTime!!)
    }
}

private fun NotificationManager.sendRandomNotification(context: Context) {

    //get data
    val gitCommandList = LoadData.getGitCommandData(context)!!.gitCommands
    val totalSize = gitCommandList.size - 1

    //get one random git command
    val randomIndex = (0..totalSize).random()
    val gitTitle = gitCommandList[randomIndex].name
    val gitCommand = gitCommandList[randomIndex].command
    val gitDescription = gitCommandList[randomIndex].description
    val gitLongDesc = gitCommand + "\n\n" + gitDescription

    val randomGitNotification =
        NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo_svg)
            .setContentTitle(gitTitle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(gitLongDesc))
            .setContentText(gitCommand).setAutoCancel(true)

    notify(Constants.NOTIFICATION_ID, randomGitNotification.build())
}
