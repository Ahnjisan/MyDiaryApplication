package com.example.mydiaryapplication

interface TodoItemClickListener {
    fun editTodoItem(todoItem: Todoitem)
    fun completeTodoItem(todoItem: Todoitem)
}