package com.vishal2376.gitcoach.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.databinding.FragmentFontSettingBinding
import com.vishal2376.gitcoach.utils.Constants

class FontSettingFragment : Fragment() {

    private var _binding: FragmentFontSettingBinding? = null
    private val binding get() = _binding!!

    private var titleSize: Float = Constants.FONT_SIZE_TITLE
    private var subTitleSize: Float = Constants.FONT_SIZE_SUB_TITLE
    private var descriptionSize: Float = Constants.FONT_SIZE_DESCRIPTION
    private var commandSize: Float = Constants.FONT_SIZE_COMMAND

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFontSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleInputs()
        handleButtons()

    }

    private fun initUI() {
        binding.tvLessonTitle.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)

        binding.layoutGitPreview.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_anim)
    }

    private fun handleButtons() {
        binding.btnReset.setOnClickListener {

            binding.apply {
                sliderTitle.value = 0f
                sliderDescription.value = 0f
                sliderCommand.value = 0f
            }

            Toast.makeText(requireContext(), "Font Size Reset to Default", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun handleInputs() {
        binding.sliderTitle.addOnChangeListener { _, value, _ ->
            titleSize = Constants.FONT_SIZE_TITLE + value
            binding.tvGitName.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
        }
        binding.sliderDescription.addOnChangeListener { _, value, _ ->
            subTitleSize = Constants.FONT_SIZE_SUB_TITLE + value
            binding.textExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, subTitleSize)

            descriptionSize = Constants.FONT_SIZE_DESCRIPTION + value
            binding.tvGitDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
            binding.tvGitExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
        }
        binding.sliderCommand.addOnChangeListener { _, value, _ ->
            commandSize = Constants.FONT_SIZE_COMMAND + value
            binding.tvGitCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, commandSize)
        }
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