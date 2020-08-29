package com.abhisekm.bitclassroom

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.ui.main.adapter.LessonsAdapter
import com.abhisekm.bitclassroom.ui.main.viewmodel.ApiStatus

@BindingAdapter("startTime")
fun bindStartTime(textView: TextView, startTime: Long?) {
    textView.let {
        val startTimeInMillis = startTime?.times(1000L)
        when (startTimeInMillis) {
            null -> textView.text = ""
            else -> textView.text =
                it.context.getString(
                    R.string.start_time,
                    convertLongToDateString(startTimeInMillis)
                )
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Lesson>?) {
    val adapter = recyclerView.adapter as LessonsAdapter
    adapter.submitList(data)
}


@BindingAdapter("apiStatus")
fun bindApiStatus(imageView: ImageView, status: ApiStatus) {
    when (status) {
        ApiStatus.LOADING -> {
            imageView.setImageResource(R.drawable.loading_animation)
            imageView.visibility = View.VISIBLE
        }
        ApiStatus.ERROR -> {
            imageView.setImageResource(R.drawable.ic_no_connection)
            imageView.visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}
