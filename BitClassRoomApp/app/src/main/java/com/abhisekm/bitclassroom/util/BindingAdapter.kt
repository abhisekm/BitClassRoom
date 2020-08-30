package com.abhisekm.bitclassroom.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.bitclassroom.R
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.ui.main.adapter.LessonsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

@BindingAdapter("localAudioEnabled")
fun bindLocalAudioEnabled(fab: FloatingActionButton, event: Event<Boolean>) {
    event.peekContent().let { muted ->
        fab.setImageResource(if (muted) R.drawable.ic_mic else R.drawable.ic_mic_off)
    }
}

@BindingAdapter("remoteAudioEnabled")
fun bindRemoteAudioEnabled(fab: FloatingActionButton, event: Event<Boolean>) {
    event.peekContent().let { muted ->
        fab.setImageResource(if (muted) R.drawable.ic_volume_on else R.drawable.ic_volume_off)
    }
}

@BindingAdapter("videoEnabled")
fun bindVideoEnabled(fab: FloatingActionButton, event: Event<Boolean>) {
    event.peekContent().let { muted ->
        fab.setImageResource(if (muted) R.drawable.ic_videocam else R.drawable.ic_videocam_off)
    }
}

@BindingAdapter("fabEnabled")
fun bindFabEnabled(fab: FloatingActionButton, enabled: Boolean) {
    fab.isEnabled = enabled
}

@BindingAdapter("remoteVideoEnabled")
fun bindRemoteVideoEnabled(textView: TextView, event: Event<Boolean>) {
    event.peekContent().let { muted ->
        textView.visibility = if (muted) View.VISIBLE else View.GONE
    }
}

