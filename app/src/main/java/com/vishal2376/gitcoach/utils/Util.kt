package com.vishal2376.gitcoach.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

fun shareCommand(context: Context, command: String) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, command)
    context.startActivity(Intent.createChooser(shareIntent, "Share Command"))
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("Git Command", text)
    clipboardManager.setPrimaryClip(clipData)
}