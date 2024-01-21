package com.vishal2376.gitcoach.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.databinding.FragmentCommunityBinding
import com.vishal2376.gitcoach.utils.Constants

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private var correctAnswers = 0
    private var incorrectAnswers = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleButtons()

    }

    private fun initUI() {
        loadData()

        binding.apply {
            tvCorrectAnswers.text = correctAnswers.toString()
            tvIncorrectAnswers.text = incorrectAnswers.toString()
        }
    }

    private fun loadData() {
        correctAnswers = requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE)
            .getInt(Constants.CORRECT_ANSWERS, 0)
        incorrectAnswers = requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE)
            .getInt(Constants.INCORRECT_ANSWERS, 0)
    }

    private fun handleButtons() {
        binding.btnStartQuiz.setOnClickListener {
            findNavController().navigate(R.id.quizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}