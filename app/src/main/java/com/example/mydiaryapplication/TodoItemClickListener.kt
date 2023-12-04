package com.example.mydiaryapplication

interface TodoItemClickListener {
    fun editTodoItem(todoItem: Todoitem)
    fun deleteTodoItem(todoItem: Todoitem)
    fun completeTodoItem(todoItem: Todoitem)
}