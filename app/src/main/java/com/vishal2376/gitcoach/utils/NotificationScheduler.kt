package com.vishal2376.gitcoach.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.vishal2376.gitcoach.services.NotificationReceiver

private const val BROADCAST_REQUEST_CODE = 0

@SuppressLint("UnspecifiedImmutableFlag")
fun scheduleNotification(context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, BROADCAST_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    //set time to 9AM
    // TODO : User can change time
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, 9)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    //set repeated alarm for notification
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
    )

}

@SuppressLint("UnspecifiedImmutableFlag")
fun cancelNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, BROADCAST_REQUEST_CODE, intent, 0)
    alarmManager.cancel(pendingIntent)
}