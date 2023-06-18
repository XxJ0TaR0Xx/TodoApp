package com.example.todoapp.model

data class TodoItem (
    var id: Int,
    var text: String,
    var importance: Importance,
    var deadline: String?,
    var flag: Boolean,
    var dateCreating: String,
    var dateChange: String
)


enum class Importance{
    LOW, NORMAL, URGENT
}