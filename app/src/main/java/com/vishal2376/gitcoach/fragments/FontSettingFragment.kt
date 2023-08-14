package com.vishal2376.gitcoach.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.databinding.FragmentFontSettingBinding
import com.vishal2376.gitcoach.utils.Category
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadSettings

class FontSettingFragment : Fragment() {

    private var _binding: FragmentFontSettingBinding? = null
    private val binding get() = _binding!!

    private var titleSize: Float = Constants.FONT_SIZE_TITLE
    private var subTitleSize: Float = Constants.FONT_SIZE_SUB_TITLE
    private var descriptionSize: Float = Constants.FONT_SIZE_DESCRIPTION
    private var commandSize: Float = Constants.FONT_SIZE_COMMAND

    private var sliderTitleValue = 0f
    private var sliderDescriptionValue = 0f
    private var sliderCommandValue = 0f

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
        initFontSize()
        initAnimation()
    }

    private fun initAnimation() {
        binding.tvLessonTitle.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)

        binding.layoutGitPreview.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_anim)
    }

    private fun initFontSize() {
        //load font size from local
        sliderTitleValue = LoadSettings.getFontSize(requireContext(), Category.FS_TITLE)
        sliderDescriptionValue = LoadSettings.getFontSize(requireContext(), Category.FS_DESCRIPTION)
        sliderCommandValue = LoadSettings.getFontSize(requireContext(), Category.FS_COMMAND)

        //update sliders
        binding.sliderTitle.value = sliderTitleValue
        binding.sliderDescription.value = sliderDescriptionValue
        binding.sliderCommand.value = sliderCommandValue

        //update UI font
        titleSize = Constants.FONT_SIZE_TITLE + sliderTitleValue
        binding.tvGitName.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)

        subTitleSize = Constants.FONT_SIZE_SUB_TITLE + sliderDescriptionValue
        binding.textExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, subTitleSize)

        descriptionSize = Constants.FONT_SIZE_DESCRIPTION + sliderDescriptionValue
        binding.tvGitDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
        binding.tvGitExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)

        commandSize = Constants.FONT_SIZE_COMMAND + sliderCommandValue
        binding.tvGitCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, commandSize)

    }

    private fun handleButtons() {
        binding.btnReset.setOnClickListener {
            binding.apply {
                sliderTitle.value = 0f
                sliderDescription.value = 0f
                sliderCommand.value = 0f
            }
        }

        binding.tvLessonTitle.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val sharedPreferences =
                requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putFloat(Category.FS_TITLE, binding.sliderTitle.value)
                putFloat(Category.FS_DESCRIPTION, binding.sliderDescription.value)
                putFloat(Category.FS_COMMAND, binding.sliderCommand.value)
            }.apply()

            Toast.makeText(requireContext(), "Font Setting Saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
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