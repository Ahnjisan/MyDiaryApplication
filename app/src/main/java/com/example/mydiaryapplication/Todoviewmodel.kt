package com.example.mydiaryapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class Todoviewmodel: ViewModel() {
    var items = MutableLiveData<MutableList<Todoitem>?>()

    init {
        items.value = mutableListOf()
    }
    fun addItem(newTodo: Todoitem){
        val list = items.value
        list!!.add(newTodo)
        items.postValue(list)
    }
    fun updateItem(id: UUID, name: String, text: String){
        val list = items.value
        val Todo = list!!.find { it.id == id }!!
        Todo.name = name
        Todo.desc = text

        items.postValue(list)
    }
    fun deleteItem(todoItem: Todoitem) {
        val list = items.value
        list?.remove(todoItem)
        items.postValue(list)
    }
    fun toggleTodoComplete(todoItem: Todoitem) {
        val list = items.value
        list?.find { it.id == todoItem.id }?.toggleComplete()
        items.postValue(list)
    }
}