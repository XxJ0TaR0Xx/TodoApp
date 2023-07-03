package com.example.todoapp.utils

import androidx.core.content.res.ResourcesCompat
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority

fun Priority.toResource() = when(this) {
    Priority.NO -> ResourcesCompat.ID_NULL
    Priority.LOW -> R.drawable.ic_priority_low_24dp
    Priority.HIGH -> R.drawable.ic_priority_high_24dp
}

fun Priority.toStringResource() = when(this) {
    Priority.NO -> R.string.priority_no
    Priority.LOW -> R.string.priority_low
    Priority.HIGH -> R.string.priority_high
}