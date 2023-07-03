package com.example.todoapp.ui.newTaskActv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.utils.dateFromLong
import com.niqr.todoapp.ui.taskEdit.model.TaskEditUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    private val repo: TodoItemsRepository
): ViewModel() {
    private var isEdit = false
    private var previousTask: TodoItem? = null

    private val _uiEvent = Channel<TaskEditUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _priority = MutableStateFlow(Priority.LOW)
    val priority = _priority.asStateFlow()

    private val _deadline = MutableStateFlow<LocalDate>(LocalDate.now().plusDays(1))
    val deadLine = _deadline.asStateFlow()

    fun updateDate(time: Long) {
        val newDate = dateFromLong(time)
        _deadline.update {
            newDate
        }
    }

    fun updatePriority(priority: Priority){
        val newPriority = priority
        _priority.update {
            newPriority
        }
    }

    fun updateText(text: String){
        val newText = text
        _text.update {
            newText
        }
    }

    fun saveTask(){
        if(_text.value.isBlank())
            return

        val newItem =
            TodoItem(
                id = "1",
                text = _text.value,
                priority = priority.value,
                deadline = _deadline.value,
                flag = false
            )

        viewModelScope.launch {
            repo.addTodoItem(newItem)
            _uiEvent.send(TaskEditUiEvent.NavigateBack)
        }
    }


}