package com.vishal2376.gitcoach.fragments

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vishal2376.gitcoach.adapters.GitInfoAdapter
import com.vishal2376.gitcoach.databinding.FragmentExploreBinding
import com.vishal2376.gitcoach.models.GitCommand

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

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
        val gitCommandList = gson.fromJson(jsonString, GitCommand::class.java)

        //set recycler view
        binding.rvGitInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GitInfoAdapter(requireContext(), gitCommandList.git_info)
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