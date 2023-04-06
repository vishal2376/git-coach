package com.vishal2376.gitcoach

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.vishal2376.gitcoach.databinding.ActivityMainBinding
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.Constants.shareMessage
import com.vishal2376.gitcoach.utils.LoadSettings

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load settings
        LoadSettings.loadTheme(this)

        // force dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivNavMenu.setOnClickListener {
            handleNavDrawer()
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemReportBug -> {
                    reportBug()
                }
                R.id.itemSuggestions -> {
                    getSuggestions()
                }
                R.id.itemRateUs -> {
                    rateUs()
                }
                R.id.itemShareApp -> {
                    shareApp()
                }
            }
            true
        }

    }

    private fun handleNavDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND);
        shareIntent.type = "text/plain";
        Constants.shareMessage += "https://play.google.com/store/apps/details?id=" + applicationContext.packageName + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Git Coach");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share This App"));
    }

    private fun reportBug() {
        val subject = "Git Coach: Report Bug"
        val uriBuilder = StringBuilder("mailto:" + Uri.encode(Constants.email))
        uriBuilder.append("?subject=" + Uri.encode(subject))
        val uriString = uriBuilder.toString()

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
        startActivity(Intent.createChooser(intent, "Report Bug"))
    }

    private fun getSuggestions() {

        val subject = "Git Coach: Suggestions"
        val uriBuilder = StringBuilder("mailto:" + Uri.encode(Constants.email))
        uriBuilder.append("?subject=" + Uri.encode(subject))
        val uriString = uriBuilder.toString()

        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(uriString))
        startActivity(Intent.createChooser(intent, "Send Suggestions"))
    }

    private fun rateUs() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
        )
        startActivity(intent)

    }

}