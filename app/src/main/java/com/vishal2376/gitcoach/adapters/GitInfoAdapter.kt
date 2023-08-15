package com.vishal2376.gitcoach.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vishal2376.gitcoach.R
import com.vishal2376.gitcoach.models.GitCommandItem
import com.vishal2376.gitcoach.utils.Category
import com.vishal2376.gitcoach.utils.Constants
import com.vishal2376.gitcoach.utils.LoadSettings
import com.vishal2376.gitcoach.utils.copyToClipboard

class GitInfoAdapter(
    private val context: Context, private var gitCommandList: List<GitCommandItem>
) : Adapter<GitInfoAdapter.GitInfoViewHolder>() {

    private var titleSize: Float = Constants.FONT_SIZE_TITLE
    private var subTitleSize: Float = Constants.FONT_SIZE_SUB_TITLE
    private var descriptionSize: Float = Constants.FONT_SIZE_DESCRIPTION
    private var commandSize: Float = Constants.FONT_SIZE_COMMAND

    init {
        initFontSize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.git_info_item, parent, false)
        return GitInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gitCommandList.size
    }

    override fun onBindViewHolder(holder: GitInfoViewHolder, position: Int) {

        //set data
        val currentGitInfo = gitCommandList[position]
        holder.name.text = currentGitInfo.name
        holder.command.text = currentGitInfo.command
        holder.description.text = currentGitInfo.description
        holder.example.text = currentGitInfo.example

        //set font size
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
        holder.exampleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, subTitleSize)
        holder.description.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
        holder.example.setTextSize(TypedValue.COMPLEX_UNIT_SP, descriptionSize)
        holder.command.setTextSize(TypedValue.COMPLEX_UNIT_SP, commandSize)

        //enable marquee effect
        holder.command.isSelected = true

        //copy to clipboard
        holder.command.setOnClickListener {
            copyToClipboard(context, holder.command.text.toString())
            Toast.makeText(context, "Command Copied", Toast.LENGTH_SHORT).show()
        }

        //animation
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.popup_anim)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(newList: List<GitCommandItem>) {
        gitCommandList = newList
        notifyDataSetChanged()
    }

    inner class GitInfoViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvGitName)
        val command: TextView = itemView.findViewById(R.id.tvGitCommand)
        val description: TextView = itemView.findViewById(R.id.tvGitDescription)
        val example: TextView = itemView.findViewById(R.id.tvGitExample)
        val exampleText: TextView = itemView.findViewById(R.id.text_example)
    }

    private fun initFontSize() {
        //load font size from local
        val sliderTitleValue = LoadSettings.getFontSize(context, Category.FS_TITLE)
        val sliderDescriptionValue = LoadSettings.getFontSize(context, Category.FS_DESCRIPTION)
        val sliderCommandValue = LoadSettings.getFontSize(context, Category.FS_COMMAND)

        titleSize = Constants.FONT_SIZE_TITLE + sliderTitleValue
        subTitleSize = Constants.FONT_SIZE_SUB_TITLE + sliderDescriptionValue
        descriptionSize = Constants.FONT_SIZE_DESCRIPTION + sliderDescriptionValue
        commandSize = Constants.FONT_SIZE_COMMAND + sliderCommandValue
    }

}