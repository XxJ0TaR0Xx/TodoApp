package com.example.todoapp

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.BusinessCellsBinding
import com.example.todoapp.model.TodoItem


class TodoItemsAdapter(
    private val actionListener: TodoItemActionListener
) : RecyclerView.Adapter<TodoItemsAdapter.TodoItemsViewHolder>(), View.OnClickListener {

    var todoItems: List<TodoItem> = emptyList()
        set(newvalue) {
            field = newvalue
            notifyDataSetChanged()
        }


    override fun onClick(v: View) {
        val item = v.tag as TodoItem
        when (v.id) {
            R.id.ic_info -> {
                showPopupMenu(v)
            }
            R.id.checkbox -> {
                //todo
            }
            else -> {
                actionListener.onItemDetails(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemsViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = BusinessCellsBinding.inflate(inflate, parent, false)

        binding.root.setOnClickListener(this)
        binding.icInfo.setOnClickListener(this)
        binding.checkbox.setOnClickListener(this)


        return TodoItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemsViewHolder, position: Int) {
        val todoItem = todoItems[position]
        with(holder.binding) {
            holder.itemView.tag = todoItem
            icInfo.tag = todoItem
            checkbox.tag = todoItem

            checkbox.isChecked = todoItem.flag
            textCells.text = todoItem.id
        }
    }

    private fun showPopupMenu(view: View){
        val popupMenu = PopupMenu(view.context, view)
        val item = view.tag as TodoItem

        popupMenu.menu.add(0,0, Menu.NONE, "Delete")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                0 -> {
                    actionListener.onItemRemove(item)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun getItemCount() = todoItems.size

    class TodoItemsViewHolder(
        val binding: BusinessCellsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}