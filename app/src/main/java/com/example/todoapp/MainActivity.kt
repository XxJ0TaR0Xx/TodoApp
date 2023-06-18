package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.model.TodoItem
import com.example.todoapp.model.TodoItemListener
import com.example.todoapp.model.TodoViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoItemsAdapter

    private val todoItemViewModel: TodoViewModel
        get() = (applicationContext as App).userService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener { view ->
            Snackbar
                .make(view, "Hello my little friend", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        adapter = TodoItemsAdapter(object : TodoItemActionListener{
            override fun onItemRemove(todoItem: TodoItem) {
                todoItemViewModel.removeItem(todoItem)
            }

            override fun onChangeFlagItem(todoItem: TodoItem) {
                todoItemViewModel.changeFlagItem(todoItem)
            }

            override fun onItemDetails(todoItem: TodoItem) {
                Toast.makeText(this@MainActivity, "Item: ${todoItem.id}", Toast.LENGTH_SHORT).show()
            }
        })

        val layoutManager = LinearLayoutManager(this )
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        todoItemViewModel.addListener(todoItemListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        todoItemViewModel.removeListener(todoItemListener)
    }

    private val todoItemListener: TodoItemListener = {
        adapter.todoItems = it
    }


}