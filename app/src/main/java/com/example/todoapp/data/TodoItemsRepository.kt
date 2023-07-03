package com.example.todoapp.data

import androidx.lifecycle.LiveData
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun todoItems(): LiveData<List<TodoItem>>
    suspend fun addTodoItem(item: TodoItem)
    suspend fun updateTodoItem(item: TodoItem)
    suspend fun deleteTodoItem(item: TodoItem)
}