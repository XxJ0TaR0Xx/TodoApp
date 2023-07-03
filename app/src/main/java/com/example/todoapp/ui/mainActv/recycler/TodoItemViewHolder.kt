package com.example.todoapp.ui.mainActv.recycler

import android.content.res.Resources
import android.graphics.Paint
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.BusinessCellsBinding
import com.example.todoapp.ui.mainActv.model.TaskUiAction
import com.example.todoapp.utils.toResource
import com.example.todoapp.utils.toStringDate

class TodoItemViewHolder(
    private val binding: BusinessCellsBinding
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var todoItem: TodoItem

    fun onBind(todoItem: TodoItem, onUiAction: (TaskUiAction) -> Unit) {
        this.todoItem = todoItem
        setUpValue()
        updateCheckBox(onUiAction)
    }

    private fun setUpValue() {
        with(binding) {
            checkbox.isChecked = todoItem.flag
            priorityIcon.setImageResource(todoItem.priority.toResource())

            textCells.text = todoItem.text
            textData.text = todoItem.deadline?.toStringDate() ?: ""

            val theme = binding.root.context.theme

            setupDescriptionView(checkbox.isChecked, theme)
            setupCheckBoxView(todoItem.priority)
        }
    }


    private fun updateCheckBox(onUiAction: (TaskUiAction) -> Unit) {
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (todoItem.flag != isChecked) {
                val newItem = todoItem.copy(flag = isChecked)
                onUiAction(TaskUiAction.UpdateTask(newItem))
            }
        }
    }

    private fun setupCheckBoxView(priority: Priority) {
        binding.checkbox.apply {
            if (priority == Priority.HIGH) {
                val tint = ContextCompat.getColorStateList(context, R.color.check_box_extra_tint)
                val drawable = ContextCompat.getDrawable(context, R.drawable.checked_turn)
                buttonDrawable = drawable
                buttonTintList = tint
            } else {
                val tint = ContextCompat.getColorStateList(context, R.color.check_box_normal_tint)
                buttonTintList = tint
            }
        }
    }

    private fun setupDescriptionView(
        isDone: Boolean,
        theme: Resources.Theme,
    ) {
        binding.textCells.apply {
            if (isDone) {
                if ((paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) != Paint.STRIKE_THRU_TEXT_FLAG)
                    paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(resources.getColor(R.color.Tertiary, theme))
            } else {
                if ((paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG)
                    paintFlags -= Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(resources.getColor(R.color.Primary, theme))
            }
        }
    }

}