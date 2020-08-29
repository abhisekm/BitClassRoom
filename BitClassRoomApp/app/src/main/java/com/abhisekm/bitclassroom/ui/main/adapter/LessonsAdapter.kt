package com.abhisekm.bitclassroom.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.bitclassroom.R
import com.abhisekm.bitclassroom.databinding.LessonItemBinding
import com.abhisekm.bitclassroom.domain.Lesson

class LessonsAdapter(private val onClickListener: OnClickListener) : ListAdapter<Lesson, LessonsAdapter.LessonViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
       return LessonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),LessonViewHolder.LAYOUT, parent, false))
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class LessonViewHolder(private val binding: LessonItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.lesson_item
        }

        fun bind(lesson: Lesson, onClickListener: OnClickListener){
            binding.lesson = lesson
            binding.btnJoin.setOnClickListener { onClickListener.onClick(lesson) }
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (lesson: Lesson) -> Unit) {
        fun onClick(lesson: Lesson) = clickListener(lesson)
    }
}