package com.vishal2376.gitcoach.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vishal2376.gitcoach.MainActivity
import com.vishal2376.gitcoach.R
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
    private var currentQuestionNumber: Int = 0

    data class QuizAnalysis(
        var correctAnswers: Int = 0,
        var incorrectAnswers: Int = 0
    )

    val quizAnalysis = QuizAnalysis()

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

        //get quiz data and pick some random questions
        gitQuizList = LoadData.getGitQuizData(requireContext())!!
        randomQuizList = selectRandomQuestions()

        if (currentQuestionNumber == 0) updateUI()

        handleButtons()
    }

    private fun checkAnswer() {
        val radioButtonId = binding.rgQuizChoice.checkedRadioButtonId
        val userAnswer =
            binding.rgQuizChoice.findViewById<RadioButton>(radioButtonId)
        val correctAnswer = randomQuizList[currentQuestionNumber].correctAnswer

        // check answer and show result
        if (userAnswer?.text.toString() != correctAnswer) {
            // todo: find correct and change background
            userAnswer?.setBackgroundResource(R.drawable.box_stroke_round_red)
            quizAnalysis.incorrectAnswers++

        } else {
            userAnswer?.setBackgroundResource(R.drawable.radio_correct_choice_bg)
            quizAnalysis.correctAnswers++
        }

        // update UI
        updateRadioButtonClick(isClickable = false)
        updateButtonText()
    }

    private fun updateButtonText() {
        if (currentQuestionNumber < Constants.DEFAULT_QUIZ_TOTAL_QUESTIONS - 1) {
            binding.btnCheckAnswer.text = getString(R.string.next_question)
        } else {
            binding.btnCheckAnswer.text = getString(R.string.finish)
        }
    }

    private fun updateRadioButtonClick(isClickable: Boolean) {
        binding.rbChoice1.isClickable = isClickable
        binding.rbChoice2.isClickable = isClickable
        binding.rbChoice3.isClickable = isClickable
        binding.rbChoice4.isClickable = isClickable
    }

    private fun selectRandomQuestions(): List<Quiz> {
        val shuffledQuestions = gitQuizList.quiz.shuffled()
        return shuffledQuestions.take(Constants.DEFAULT_QUIZ_TOTAL_QUESTIONS)
    }

    private fun updateUI() {
        setDefaultUI()

        // set question and its choices
        randomQuizList[currentQuestionNumber].let {
            binding.tvQuestionTitle.text = it.question
            binding.rbChoice1.text = it.choices[0]
            binding.rbChoice2.text = it.choices[1]
            binding.rbChoice3.text = it.choices[2]
            binding.rbChoice4.text = it.choices[3]
        }
    }

    private fun setDefaultUI() {
        // default button text
        binding.btnCheckAnswer.text = getString(R.string.check_answer)

        // set default radio button ui
        setDefaultRadioButtons()
    }

    private fun setDefaultRadioButtons() {
        binding.apply {
            rgQuizChoice.clearCheck()
            rbChoice1.setBackgroundResource(R.drawable.radio_bg_selector)
            rbChoice2.setBackgroundResource(R.drawable.radio_bg_selector)
            rbChoice3.setBackgroundResource(R.drawable.radio_bg_selector)
            rbChoice4.setBackgroundResource(R.drawable.radio_bg_selector)

            //enable radio buttons
            updateRadioButtonClick(isClickable = true)
        }
    }

    private fun handleButtons() {
        binding.btnCheckAnswer.setOnClickListener {
            when (binding.btnCheckAnswer.text) {
                getString(R.string.check_answer) -> {
                    checkAnswer()
                }

                getString(R.string.next_question) -> {
                    currentQuestionNumber++
                    updateUI()
                }

                getString(R.string.finish) -> {
                    //todo: show results and then exit
                    Log.e("@@@", "handleButtons: $quizAnalysis")
                    findNavController().popBackStack()
                }
            }
        }
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