package com.example.todoapp

import com.example.todoapp.model.TodoItem

interface TodoItemActionListener {

    fun onItemRemove(todoItem: TodoItem)

    fun onChangeFlagItem(todoItem: TodoItem)

    fun onItemDetails(todoItem: TodoItem)

}