package com.example.todoapp.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TodoItem (
    var id: String,
    var text: String,
    var priority: Priority,
    var deadline: LocalDate?,
    var flag: Boolean,
    var dateCreating: LocalDateTime = LocalDateTime.now(),
    var dateChange: LocalDateTime = LocalDateTime.now()
)


enum class Priority{
    NO, LOW, HIGH
}