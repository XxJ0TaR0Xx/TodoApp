package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.mainActv.TodoViewModel
import com.example.todoapp.ui.mainActv.model.TaskUiAction
import com.example.todoapp.ui.mainActv.recycler.SwipeCallback
import com.example.todoapp.ui.mainActv.recycler.TodoItemsAdapter
import com.example.todoapp.ui.newTaskActv.NewTaskActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val todoViewModel by viewModels<TodoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fab.setOnClickListener { startNewTask() }
            newItemField.setOnClickListener { startNewTask() }
        }

        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        Log.d("tag", "onResume")
    }

    private fun setupRecycler() {

        val layoutManager = LinearLayoutManager(this)
        val adapter = TodoItemsAdapter(todoViewModel::onUiAction)

        binding.todoItemsRecycler.adapter = adapter
        binding.todoItemsRecycler.layoutManager = layoutManager
        setupRecyclerViewSwipes(adapter, binding.todoItemsRecycler)

        Log.d("tag", " 1-${todoViewModel.todoItems()}")

        lifecycleScope.launch {
            todoViewModel.todoItems().observe(this@MainActivity){
                adapter.submitList(it)
            }

        }

        Log.d("tag", " 2-${todoViewModel.todoItems()}")
    }


    private fun setupRecyclerViewSwipes(adapter: TodoItemsAdapter, recyclerView: RecyclerView) {
        val swipe = SwipeCallback(
            context = applicationContext,
            onLeftSwipe = { position ->
                val item = adapter.getItem(position)
                todoViewModel.onUiAction(TaskUiAction.DeleteTask(item))
            },
            onRightSwipe = { position ->
                val item = adapter.getItem(position = position)
                val newItem = item.copy(flag = !item.flag)
                todoViewModel.onUiAction(TaskUiAction.UpdateTask(newItem))
            }
        )
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.todoItemsRecycler)
    }

    private fun startNewTask() {
        val intent = Intent(this, NewTaskActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)
    }


}