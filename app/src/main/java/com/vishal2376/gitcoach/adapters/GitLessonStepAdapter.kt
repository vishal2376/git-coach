package com.vishal2376.gitcoach.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.models.lesson.GitLessonItem

class GitLessonStepAdapter(
    private val context: Context,
    private var gitLesson: GitLessonItem
) :
    Adapter<GitLessonStepAdapter.GitLessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitLessonViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.git_lesson_step_item, parent, false)
        return GitLessonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gitLesson.Steps.size
    }

    override fun onBindViewHolder(holder: GitLessonViewHolder, position: Int) {

        //set data
        val currentLessonStep = gitLesson.Steps[position]
        holder.lessonDescription.text = currentLessonStep.Description
        holder.lessonExplanation.text = currentLessonStep.Explanation
        holder.lessonExample.text = currentLessonStep.Example

        //animation
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.popup_anim)

    }

    inner class GitLessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val lessonDescription: TextView = itemView.findViewById(R.id.tvLessonDescription)
        val lessonExplanation: TextView = itemView.findViewById(R.id.tvLessonExplanation)
        val lessonExample: TextView = itemView.findViewById(R.id.tvLessonExample)
    }

}