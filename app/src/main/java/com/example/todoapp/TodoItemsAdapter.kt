package com.example.todoapp

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.BusinessCellsBinding
import com.example.todoapp.model.TodoItem
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class TodoItemsAdapter(
    private val actionListener: TodoItemActionListener,
    private val context: Context
) : RecyclerView.Adapter<TodoItemsAdapter.TodoItemsViewHolder>(), View.OnClickListener {


    private val swipeCallback = SwipeCallback()
    private val itemTouchHelper = ItemTouchHelper(swipeCallback)

    var todoItems: List<TodoItem> = emptyList()
        set(newvalue) {
            field = newvalue
            notifyDataSetChanged()
        }


    override fun onClick(v: View) {
        val item = v.tag as TodoItem
        when (v.id) {
            R.id.checkbox -> {
                actionListener.onChangeFlagItem(item)
            }
            else -> {
                actionListener.onItemDetails(item)
            }
        }
    }

    inner class SwipeCallback: ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(0, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.Red))
                .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.Green))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24dp)
                .addSwipeRightActionIcon(R.drawable.check)
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.absoluteAdapterPosition
            val item = todoItems[position]

            when(direction){

                ItemTouchHelper.LEFT -> {
                    actionListener.onItemRemove(item)
                }

                ItemTouchHelper.RIGHT -> {
                    actionListener.onChangeFlagItem(item)
                }

            }
        }

    }
    fun attachItemTouchHelperToRecyclerView(recyclerView: RecyclerView){
        itemTouchHelper.attachToRecyclerView(recyclerView)
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
            textCells.text = todoItem.id.toString()
        }


    }

//    private fun showPopupMenu(view: View){
//        val popupMenu = PopupMenu(view.context, view)
//        val item = view.tag as TodoItem
//
//        popupMenu.menu.add(0,0, Menu.NONE, "Delete")
//
//        popupMenu.setOnMenuItemClickListener {
//            when (it.itemId) {
//                0 -> {
//                    actionListener.onItemRemove(item)
//                }
//            }
//            return@setOnMenuItemClickListener true
//        }
//
//        popupMenu.show()
//    }


    override fun getItemCount() = todoItems.size

    class TodoItemsViewHolder(
        val binding: BusinessCellsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}