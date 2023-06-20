package com.example.todoapp.model

import com.github.javafaker.Faker


typealias TodoItemListener = (todoItem: List<TodoItem>) -> Unit

class TodoViewModel {

    private var todoItems = mutableListOf<TodoItem>()

    private val listeners = mutableSetOf<TodoItemListener>()

    init {
        val faker = Faker.instance()
        todoItems = (0..10).map { TodoItem(
            id = it,
            text = faker.expression("lpl"),
            importance = Importance.LOW,
            deadline = faker.date().toString(),
            flag = false,
            dateCreating = faker.date().toString(),
            dateChange = faker.date().toString()
        ) }.toMutableList()
    }



    fun getItemList(): List<TodoItem>{
        return todoItems
    }


//    fun removeItemAt(position: Int){
//        todoItems.removeAt(position)
//        notifyChange()
//    }

    fun removeItem(item: TodoItem) {
        val indexToDelete = todoItems.indexOfFirst { it.id == item.id }
        if (indexToDelete != -1){
            todoItems.removeAt(indexToDelete)
            notifyChange()
        }
    }

    fun changeFlagItem(item: TodoItem){
        item.flag = true
        notifyChange()
    }


    fun addListener(listener: TodoItemListener){
        listeners.add(listener)
        listener.invoke(todoItems)
    }

    fun removeListener(listener: TodoItemListener){
        listeners.remove(listener)
    }

    private fun notifyChange() {
        listeners.forEach { it.invoke(todoItems) }
    }

}