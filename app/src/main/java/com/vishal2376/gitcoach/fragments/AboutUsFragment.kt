package com.vishal2376.gitcoach.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.databinding.FragmentAboutUsBinding
import com.vishal2376.gitcoach.utils.Constants

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleButtons()
    }

    private fun handleButtons() {
        binding.tvAboutDeveloper.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.apply {
            btnTwitter.setOnClickListener {
                openUrl(Constants.TWITTER_LINK)
            }
            btnGithub.setOnClickListener {
                openUrl(Constants.GITHUB_LINK)
            }
            btnInstagram.setOnClickListener {
                openUrl(Constants.INSTAGRAM_LINK)
            }
            btnLinkedin.setOnClickListener {
                openUrl(Constants.LINKEDIN_LINK)
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(url)
        )
        startActivity(intent)
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