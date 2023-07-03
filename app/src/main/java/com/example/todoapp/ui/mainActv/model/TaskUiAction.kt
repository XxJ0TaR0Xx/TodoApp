package com.example.todoapp.ui.mainActv.model

import com.example.todoapp.data.model.TodoItem

sealed class TaskUiAction {
    data class UpdateTask(val todoItem: TodoItem): TaskUiAction()
    data class DeleteTask(val todoItem: TodoItem): TaskUiAction()
    //data class EditTask(val todoItem: TodoItem): TaskUiAction()
}