package com.example.todoapp.ui.mainActv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.local.LocalTodoItemRepository
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.ui.mainActv.model.TaskUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repo: TodoItemsRepository
): ViewModel() {

//    private val _uiEvent = Channel<TaskUiAction>()
//    val uiEvent = _uiEvent.receiveAsFlow()


    fun onUiAction(action: TaskUiAction) {
        when(action) {
            is TaskUiAction.UpdateTask -> updateTodoItem(action.todoItem)
            is TaskUiAction.DeleteTask -> removeItem(action.todoItem)
        }
    }

    fun todoItems() =
        repo.todoItems()

//    fun removeItemAt(position: Int){
//        todoItems.removeAt(position)
//        notifyChange()
//    }

    private fun updateTodoItem (item: TodoItem) {
        viewModelScope.launch {
            repo.updateTodoItem(item)
        }
    }

    private fun removeItem(item: TodoItem){
        viewModelScope.launch {
            repo.deleteTodoItem(item)
        }
    }


}
