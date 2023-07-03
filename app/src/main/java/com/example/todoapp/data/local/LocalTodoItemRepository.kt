package com.example.todoapp.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class LocalTodoItemRepository @Inject constructor(): TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = sampleTasks.toMutableList()

    private val _getTodoItem = MutableLiveData(todoItems.toList())
//
//    override fun todoItems(): Flow<List<TodoItem>> =
//        getTodoItem.asStateFlow()

    override fun todoItems(): LiveData<List<TodoItem>> = _getTodoItem


    override suspend fun addTodoItem(item: TodoItem) {
        Log.d("TAG", "1- $todoItems")
        val taskId = ((todoItems.lastOrNull()?.id ?: "0").toInt() + 1).toString()
        todoItems.add(item.copy(id = taskId))
        _getTodoItem.value = todoItems.toList()
        Log.d("TAG", "2- $todoItems")
    }

    override suspend fun updateTodoItem(item: TodoItem) {
        val index = todoItems.indexOfFirst { it.id == item.id }
        if (index == -1)
            return
        todoItems[index] = item
        _getTodoItem.value = todoItems.toList()
    }

    override suspend fun deleteTodoItem(item: TodoItem) {
        val indexToDelete = todoItems.indexOfFirst { it.id == item.id }
        if (indexToDelete != -1){
            todoItems.removeAt(indexToDelete)
            _getTodoItem.value = todoItems.toList()
        }
    }

//    private fun notifyChange(){
//        MainScope().launch {
//            getTodoItem.update {
//                todoItems.map { it.copy() }.toList()
//            }
//        }
//     }

}
private val sampleTasks = listOf(
    TodoItem(
        id = "1",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        flag = true,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "2",
        text = "Simple task of Yandex Senior Android developer,Simple task of Yandex Senior Android " +
                "developer,Simple task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        flag = false,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "3",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.NO,
        flag = false,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "4",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.NO,
        flag = false,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "5",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        flag = false,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "6",
        text = " Android developer",
        priority = Priority.LOW,
        flag = true,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "7",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        flag = true,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "8",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.NO,
        flag = true,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "9",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.LOW,
        flag = true,
        deadline = LocalDate.now()
    ),
    TodoItem(
        id = "10",
        text = "Simple task of Yandex Senior Android developer",
        priority = Priority.HIGH,
        flag = true,
        deadline = LocalDate.now()
    )
)