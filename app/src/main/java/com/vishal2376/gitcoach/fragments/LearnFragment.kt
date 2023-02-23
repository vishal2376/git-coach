package com.vishal2376.gitcoach.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vishal2376.gitcoach.adapters.GitLessonAdapter
import com.vishal2376.gitcoach.databinding.FragmentLearnBinding
import com.vishal2376.gitcoach.models.lesson.GitLesson
import com.vishal2376.gitcoach.utils.LoadData
import com.vishal2376.gitcoach.utils.LoadSettings

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var gitLessonAdapter: GitLessonAdapter
    private lateinit var gitLessonList: GitLesson

    private var lessonProgress: Int? = 0

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //load settings
        LoadSettings.loadTheme(requireContext())

        //get progress
        lessonProgress = getLessonProgress()

        // Inflate the layout for this fragment
        _binding = FragmentLearnBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get json data
        gitLessonList = LoadData.getGitLessonData(requireContext())!!

        val progressValue = ((getLessonProgress().toFloat() / gitLessonList.gitLessons.size.toFloat()) * 100).toInt()
        Log.e("@@@", "onViewCreated: ${getLessonProgress()}")
        binding.circularProgressBar.progress = progressValue.toFloat()
        binding.tvProgress.text = "${progressValue}%"

        gitLessonAdapter =
            GitLessonAdapter(
                requireContext(),
                gitLessonList.gitLessons,
                lessonProgress!!,
                ::onLessonItemClicked
            )

        //set recycler view
        binding.rvGitInfo.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gitLessonAdapter
        }

    }

    private fun onLessonItemClicked(currentLesson: Int) {
        val action = MainFragmentDirections.actionMainFragmentToLessonFragment(currentLesson)
        findNavController().navigate(action)
    }

    private fun getLessonProgress(): Int {
        //load saved progress
        return requireContext().getSharedPreferences("PROGRESS", MODE_PRIVATE)
            .getInt("LESSON", 0)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}