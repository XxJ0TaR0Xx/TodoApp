package com.example.todoapp.ui.mainActv.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.BusinessCellsBinding
import com.example.todoapp.ui.mainActv.model.TaskUiAction


class TodoItemsAdapter(
    private val onUiAction: (TaskUiAction) -> Unit
) : ListAdapter<TodoItem ,TodoItemViewHolder>(DiffCallback()) {


    public override fun getItem(position: Int): TodoItem {
        return super.getItem(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = BusinessCellsBinding.inflate(inflate, parent, false)
        return TodoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(
            getItem(position),
            onUiAction
        )
    }
}