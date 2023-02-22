package com.vishal2376.gitcoach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.vishal2376.gitcoach.databinding.ActivitySplashScreenBinding
import com.vishal2376.gitcoach.utils.LoadSettings

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load settings
        LoadSettings.loadTheme(this)

        //set binding
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //animation
        loadAnimation()

        //load main activity after some time
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

    private fun loadAnimation() {
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)
        binding.tvAppTitle.animation = textAnim

        val imgAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
        binding.ivAppLogo.animation = imgAnim
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}