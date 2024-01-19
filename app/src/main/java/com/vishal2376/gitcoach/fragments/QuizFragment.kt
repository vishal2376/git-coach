package com.vishal2376.gitcoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.databinding.FragmentQuizBinding
import com.vishal2376.gitcoach.models.quiz.GitQuiz
import com.vishal2376.gitcoach.models.quiz.Quiz
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadData
import com.vishal2376.gitcoach.utils.LoadSettings


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var gitQuizList: GitQuiz
    private lateinit var randomQuizList: List<Quiz>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //load settings
        LoadSettings.loadTheme(requireContext())

        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        //get data
        gitQuizList = LoadData.getGitQuizData(requireContext())!!
        randomQuizList = selectRandomQuestions()

        handleQuiz()
        handleButtons()
    }

    private fun selectRandomQuestions(): List<Quiz> {
        val shuffledQuestions = gitQuizList.quiz.shuffled()
        return shuffledQuestions.take(Constants.DEFAULT_QUIZ_TOTAL_QUESTIONS)
    }

    private fun handleQuiz() {
        binding.tvQuestionTitle.text = randomQuizList[0].question
    }

    private fun handleButtons() {
        binding.btnCheckAnswer.setOnClickListener {}
    }

    override fun onResume() {
        super.onResume()
        MainActivity.appBarLayout.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}