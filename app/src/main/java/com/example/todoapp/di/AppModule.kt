package com.example.todoapp.di

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.local.LocalTodoItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoItemRepository(): TodoItemsRepository {
        return LocalTodoItemRepository()
    }
}