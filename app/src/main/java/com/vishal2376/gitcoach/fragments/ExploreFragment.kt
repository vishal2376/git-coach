package com.vishal2376.gitcoach.fragments

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.adapters.GitInfoAdapter
import com.vishal2376.gitcoach.databinding.FragmentExploreBinding
import com.vishal2376.gitcoach.models.GitCommand
import com.vishal2376.gitcoach.models.GitCommandItem
import java.util.*

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var gitInfoAdapter: GitInfoAdapter
    private lateinit var gitCommandList: GitCommand

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //load settings
        loadSettings()

        // Inflate the layout for this fragment
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get data
        val gson = Gson()
        val jsonString = requireActivity().assets.readFile("git_commands.json")
        gitCommandList = gson.fromJson(jsonString, GitCommand::class.java)

        gitInfoAdapter = GitInfoAdapter(requireContext(), gitCommandList.gitCommands)

        //set recycler view
        binding.rvGitInfo.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gitInfoAdapter
        }

        //search data
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun loadSettings() {
        //load saved values
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val userTheme = sp.getString("user_theme", "yellow")

        //set theme
        setUserTheme(userTheme)
    }

    private fun setUserTheme(userTheme: String?) {
        when (userTheme) {
            "red" -> {
                requireActivity().setTheme(R.style.Theme_RED)
            }
            "blue" -> {
                requireActivity().setTheme(R.style.Theme_BLUE)
            }
            "green" -> {
                requireActivity().setTheme(R.style.Theme_GREEN)
            }
            else -> {
                requireActivity().setTheme(R.style.Theme_GitCoach)
            }
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<GitCommandItem>()
            for (i in gitCommandList.gitCommands) {
                if (i.command.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                gitInfoAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}