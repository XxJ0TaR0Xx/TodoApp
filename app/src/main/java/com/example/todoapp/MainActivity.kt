package com.example.todoapp

import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.BusinessCellsBinding
import com.example.todoapp.model.TodoItem
import com.example.todoapp.model.TodoItemListener
import com.example.todoapp.model.TodoViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recbinding: BusinessCellsBinding
    private lateinit var adapter: TodoItemsAdapter

    private val todoItemViewModel: TodoViewModel
        get() = (applicationContext as App).todoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        recbinding = BusinessCellsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this

        binding.fab.setOnClickListener { view ->
            Snackbar
                .make(view, "Hello my little friend", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        adapter = TodoItemsAdapter(object : TodoItemActionListener {

            override fun onItemRemove(todoItem: TodoItem) {
                todoItemViewModel.removeItem(todoItem)
                Toast.makeText(context, "Свайпнут элемент: ${todoItem.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onChangeFlagItem(todoItem: TodoItem) {
                todoItemViewModel.changeFlagItem(todoItem)
            }

            override fun onItemDetails(todoItem: TodoItem) {
                Toast.makeText(this@MainActivity, "Item: ${todoItem.id}", Toast.LENGTH_SHORT).show()
            }
        } ,this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.attachItemTouchHelperToRecyclerView(binding.recyclerView)
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