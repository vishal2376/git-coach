package com.vishal2376.gitcoach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import com.vishal2376.gitcoach.adapters.ViewPagerAdapter
import com.vishal2376.gitcoach.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //load settings
        loadSettings()

        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Learn"
                }
                1 -> {
                    tab.text = "Reference"
                }
            }
        }.attach()

    }


    private fun loadSettings() {
        //load saved values
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val userTheme = sp.getString("user_theme", "yellow")

        //set theme
        setUserTheme(userTheme)
    }

    private fun setUserTheme(userTheme: String?) {
        when (userTheme) {
            "red" -> {
                requireActivity().setTheme(R.style.Theme_RED)
            }
            "blue" -> {
                requireActivity().setTheme(R.style.Theme_BLUE)
            }
            "green" -> {
                requireActivity().setTheme(R.style.Theme_GREEN)
            }
            else -> {
                requireActivity().setTheme(R.style.Theme_GitCoach)
            }
        }
    }
}