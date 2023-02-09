package com.vishal2376.gitcoach.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.vishal2376.gitcoach.adapters.ViewPagerAdapter
import com.vishal2376.gitcoach.databinding.FragmentMainBinding
import com.vishal2376.gitcoach.utils.LoadSettings

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //load settings
        LoadSettings.loadTheme(requireContext())

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

        binding.btnChangeTheme.setOnClickListener {
            changeUserTheme()
            restartApp()
        }

    }

    private fun restartApp() {
        val intent = requireActivity().intent
        requireActivity().let {
            it.finish()
            it.startActivity(intent)
        }

    }

    private fun changeUserTheme() {
        val themes = listOf("red", "blue", "yellow", "green")

        val randNumber = (0..3).random()

        requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE).edit()
            .putString("user_theme", themes[randNumber]).apply();
    }

}