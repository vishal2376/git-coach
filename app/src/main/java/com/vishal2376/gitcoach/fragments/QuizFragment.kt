package com.vishal2376.gitcoach.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
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

    private var correctAnswers: Int = 0
    private var incorrectAnswers: Int = 0

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

        initUI()
        handleButtons()
        handleInput()
    }

    private fun initUI() {
        initAnimation()
    }

    private fun initAnimation() {
        binding.textQuiz.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)
        binding.tvQuestionNumber.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)
    }


    private fun handleButtons() {
        binding.textQuiz.setOnClickListener {
            findNavController().popBackStack()
        }

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
                    saveResult()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun saveResult() {
        val sharedPreferences =
            requireContext().getSharedPreferences("SETTINGS", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt(Constants.CORRECT_ANSWERS, correctAnswers)
            putInt(Constants.INCORRECT_ANSWERS, incorrectAnswers)
        }.apply()
    }

    private fun handleInput() {
        binding.rgQuizChoice.setOnCheckedChangeListener { _, _ ->
            if (binding.btnCheckAnswer.text == getString(R.string.check_answer)) {
                binding.btnCheckAnswer.isEnabled = true
            }
        }
    }

    private fun checkAnswer() {
        val radioButtonId = binding.rgQuizChoice.checkedRadioButtonId
        val userAnswer =
            binding.rgQuizChoice.findViewById<RadioButton>(radioButtonId)
        val correctAnswer = randomQuizList[currentQuestionNumber].correctAnswer

        // check answer and show result
        if (userAnswer?.text.toString() == correctAnswer) {
            // correct user answer
            userAnswer?.setBackgroundResource(R.drawable.radio_correct_choice_bg)
            correctAnswers++
        } else {
            //incorrect user answer
            userAnswer?.setBackgroundResource(R.drawable.box_stroke_round_red)
            incorrectAnswers++

            // actual answer
            val correctAnswerRadioButton = getCorrectAnswerId()
            correctAnswerRadioButton.setBackgroundResource(R.drawable.radio_correct_choice_bg)
        }

        // update UI
        updateRadioButtonClick(isClickable = false)
        updateButtonText()
    }

    private fun getCorrectAnswerId(): RadioButton {
        var correctAnswer: RadioButton? = null
        val radioGroup = binding.rgQuizChoice

        // check all radio buttons
        for (i in 0 until (radioGroup.childCount)) {
            val id = radioGroup.getChildAt(i).id
            correctAnswer = binding.rgQuizChoice.findViewById(id)
            if (correctAnswer.text == randomQuizList[currentQuestionNumber].correctAnswer) {
                break
            }
        }

        return correctAnswer!!
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

        //show current/total questions
        binding.tvQuestionNumber.text = getString(
            R.string.remaining_questions,
            (currentQuestionNumber + 1),
            Constants.DEFAULT_QUIZ_TOTAL_QUESTIONS
        )
    }

    private fun setDefaultUI() {
        // set default radio button ui
        setDefaultRadioButtons()

        // default button text
        binding.btnCheckAnswer.text = getString(R.string.check_answer)
        binding.btnCheckAnswer.isEnabled = false
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

    private fun selectRandomQuestions(): List<Quiz> {
        val shuffledQuestions = gitQuizList.quiz.shuffled()
        return shuffledQuestions.take(Constants.DEFAULT_QUIZ_TOTAL_QUESTIONS)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.appBarLayout.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.appBarLayout.visibility = View.VISIBLE
        _binding = null
    }
}