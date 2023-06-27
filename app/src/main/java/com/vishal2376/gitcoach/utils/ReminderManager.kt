package com.vishal2376.gitcoach.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.vishal2376.gitcoach.services.AlarmReceiver
import java.util.Locale

private const val REMINDER_REQUEST_CODE = 200

object ReminderManager {

    fun startReminder(context: Context, reminderTime: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //get hours and minutes
        val (hours, min) = reminderTime.split(":").map { it.toInt() }

        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(
                context.applicationContext,
                REMINDER_REQUEST_CODE,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }


        val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, min)
        }

        //schedule next day if user set current time
        if (Calendar.getInstance(Locale.ENGLISH)
                .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
        ) {
            calendar.add(Calendar.DATE, 1)
        }

        //set alarm
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent), intent
        )

    }

    fun stopReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(
                context.applicationContext, REMINDER_REQUEST_CODE, it, PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }

}