package com.example.todoapp

import android.app.Application
import com.example.todoapp.model.TodoViewModel

class App: Application() {

    val userService = TodoViewModel()
}