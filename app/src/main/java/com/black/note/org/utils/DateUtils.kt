package com.black.note.org.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("formatDate")
fun TextView.setDate(date: Date?) {
    date?.let {
        text = dateFormat(date)
    }
}

private fun dateFormat(date: Date): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return format.format(date)
}