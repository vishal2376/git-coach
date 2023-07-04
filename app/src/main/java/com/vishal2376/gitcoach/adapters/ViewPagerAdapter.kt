package com.vishal2376.gitcoach.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vishal2376.gitcoach.fragments.CommunityFragment
import com.vishal2376.gitcoach.fragments.ExploreFragment
import com.vishal2376.gitcoach.fragments.LearnFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LearnFragment()
            }

            1 -> {
                ExploreFragment()
            }

            2 -> {
                CommunityFragment()
            }

            else -> {
                LearnFragment()
            }
        }
    }
}