package com.example.mydiaryapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.util.Log
import java.util.UUID


class Todoviewmodel: ViewModel() {
    var items = MutableLiveData<MutableList<Todoitem>?>()
    private val database = FirebaseDatabase.getInstance().getReference("todoItems")

    init {
        loadTodoItems()
    }
    private fun loadTodoItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList = mutableListOf<Todoitem>()
                snapshot.children.forEach {
                    val todoItem = it.getValue(Todoitem::class.java)
                    todoItem?.let { todoList.add(it) }
                }
                items.value = todoList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load todo items", error.toException())
            }
        })
    }

    fun addItem(newTodo: Todoitem){
        val newTodoKey = database.push().key
        Log.d("AddItem", "Generated key: $newTodoKey")
        newTodoKey?.let {
            database.child(it).setValue(newTodo).addOnSuccessListener {
                // 성공 시 필요한 동작(예: 로그 출력)
            }.addOnFailureListener {
                // 실패 시 오류 처리
            }
        } ?: Log.e("AddItem", "Failed to generate a new key")
    }
    fun updateItem(id: String, name: String, text: String){
        val list = items.value
        val Todo = list!!.find { it.id == id }!!
        Todo.name = name
        Todo.desc = text
        items.postValue(list)
        val todoMap = mapOf("name" to name, "desc" to text)
        database.child(id).updateChildren(todoMap)
            .addOnSuccessListener {
                // 업데이트 성공 시 처리
            }
            .addOnFailureListener {
                // 업데이트 실패 시 처리
            }
    }
    fun deleteItem(todoItem: Todoitem) {
        // Firebase 데이터베이스에서 항목 삭제
        database.child(todoItem.id).removeValue()
            .addOnSuccessListener {
                // 삭제 성공 시 처리
            }
            .addOnFailureListener {
                // 삭제 실패 시 처리
            }
    }
    fun toggleTodoComplete(todoItem: Todoitem) {
        val list = items.value
        list?.find { it.id == todoItem.id }?.toggleComplete()
        items.postValue(list)
    }
}