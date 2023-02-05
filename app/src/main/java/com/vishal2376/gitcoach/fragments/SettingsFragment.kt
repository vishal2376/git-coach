package com.vishal2376.gitcoach.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.vishal2376.gitcoach.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)



    }
}