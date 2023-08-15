package com.vishal2376.gitcoach.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.models.lesson.GitLessonItem
import com.vishal2376.gitcoach.utils.Category
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadSettings
import com.vishal2376.gitcoach.utils.copyToClipboard

class GitLessonStepAdapter(
    private val context: Context,
    private var gitLesson: GitLessonItem
) :
    Adapter<GitLessonStepAdapter.GitLessonViewHolder>() {

    private var titleSize: Float = Constants.FONT_SIZE_TITLE
    private var descriptionSize: Float = Constants.FONT_SIZE_DESCRIPTION
    private var commandSize: Float = Constants.FONT_SIZE_COMMAND

    init {
        initFontSize()
    }

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

        //set font size
        holder.lessonDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
        holder.lessonExplanation.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
        holder.lessonExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, commandSize)


        if (currentLessonStep.Example.isEmpty())
            holder.lessonExample.visibility = View.GONE
        else
            holder.lessonExample.text = currentLessonStep.Example

        //copy to clipboard
        holder.lessonExample.setOnClickListener {
            copyToClipboard(context, holder.lessonExample.text.toString())
            Toast.makeText(context, "Command Copied", Toast.LENGTH_SHORT).show()
        }
        //hide vertical line of last element
        if (position == gitLesson.Steps.size - 1)
            holder.verticalLine.visibility = View.GONE

        //animation
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.popup_anim)

    }

    inner class GitLessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val lessonDescription: TextView = itemView.findViewById(R.id.tvLessonDescription)
        val lessonExplanation: TextView = itemView.findViewById(R.id.tvLessonExplanation)
        val lessonExample: TextView = itemView.findViewById(R.id.tvLessonExample)
        val verticalLine: ImageView = itemView.findViewById(R.id.ivVerticalLine)
    }

    private fun initFontSize() {
        //load font size from local
        val sliderTitleValue = LoadSettings.getFontSize(context, Category.FS_TITLE)
        val sliderDescriptionValue = LoadSettings.getFontSize(context, Category.FS_DESCRIPTION)
        val sliderCommandValue = LoadSettings.getFontSize(context, Category.FS_COMMAND)

        titleSize = Constants.FONT_SIZE_TITLE + sliderTitleValue
        descriptionSize = Constants.FONT_SIZE_DESCRIPTION + sliderDescriptionValue
        commandSize = Constants.FONT_SIZE_COMMAND + sliderCommandValue
    }

}