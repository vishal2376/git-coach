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

class GitLessonAdapter(
    private val context: Context,
    private var gitLessonList: List<GitLessonItem>,
    private val onLessonItemClicked: (Int) -> Unit
) :
    Adapter<GitLessonAdapter.GitLessonViewHolder>() {

    private var isLessonLock: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitLessonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.git_lesson_item, parent, false)
        return GitLessonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gitLessonList.size
    }

    override fun onBindViewHolder(holder: GitLessonViewHolder, position: Int) {

        //set data
        val currentGitLesson = gitLessonList[position]
        holder.lessonCount.text = (position + 1).toString()
        holder.lessonTitle.text = currentGitLesson.LessonTitle

        //animation
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.popup_anim)

        holder.itemView.setOnClickListener {
            onLessonItemClicked(position)
        }

    }

    inner class GitLessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.tvLessonTitle)
        val lessonCount: TextView = itemView.findViewById(R.id.tvLessonCount)
    }

}