package com.vishal2376.gitcoach.fragments

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
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

        gitInfoAdapter = GitInfoAdapter(requireContext(), gitCommandList.git_info)

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

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<GitCommandItem>()
            for (i in gitCommandList.git_info) {
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