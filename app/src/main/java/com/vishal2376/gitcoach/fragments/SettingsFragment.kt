package com.vishal2376.gitcoach.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.databinding.FragmentSettingsBinding
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadSettings
import com.vishal2376.gitcoach.utils.LocaleHelper

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleButtons()

    }

    private fun initUI() {
        loadDefaultValue()
        initAnimation()
    }

    private fun loadDefaultValue() {
        val locale = LoadSettings.getLocale(requireContext())
        val currentLanguage = when (locale) {
            "en" -> binding.rgEnglish
            "zh" -> binding.rbChinese
            "de" -> binding.rgGerman
            "it" -> binding.rgItalian
            "pt" -> binding.rgPortuguese
            "ru" -> binding.rgRussian
            "es" -> binding.rgSpanish
            "tr" -> binding.rbTurkish
            else -> binding.rgEnglish
        }
        currentLanguage.isChecked = true
    }

    private fun initAnimation() {
        binding.tvSettingsTitle.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)

    }

    private fun handleButtons() {

        binding.tvSettingsTitle.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {


            val selectedLanguageLocale = when (binding.rgLanguage.checkedRadioButtonId) {
                R.id.rgEnglish -> "en"
                R.id.rbChinese -> "zh"
                R.id.rgGerman -> "de"
                R.id.rgItalian -> "it"
                R.id.rgPortuguese -> "pt"
                R.id.rgRussian -> "ru"
                R.id.rgSpanish -> "es"
                R.id.rbTurkish -> "tr"
                else -> "en"
            }

            //save language locale
            saveLocaleCode(selectedLanguageLocale)

            //update language in app
            updateLocale(selectedLanguageLocale)

            Toast.makeText(requireContext(), "Language Changed", Toast.LENGTH_SHORT).show()

            restartApp()
        }
    }

    private fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun updateLocale(selectedLocale: String) {
        val updatedContext = LocaleHelper.setLocale(requireContext(), selectedLocale)
        resources.updateConfiguration(
            updatedContext.resources.configuration,
            updatedContext.resources.displayMetrics
        )
    }


    private fun saveLocaleCode(selectedLanguageLocale: String) {
        val sharedPreferences =
            requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.LOCALE, selectedLanguageLocale).apply()
    }

    override fun onResume() {
        super.onResume()
        MainActivity.appBarLayout.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.appBarLayout.visibility = View.VISIBLE
        _binding = null
    }
}