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
                snapshot.children.forEach { data ->
                    val id = data.key
                    val name = data.child("name").getValue(String::class.java)
                    val isCompleted = data.child("isCompleted").getValue(Boolean::class.java) ?: false // 기본값으로 false를 사용하거나 필요에 따라 다른 기본값 사용


                    if (id != null && name != null && isCompleted != null) {
                        val todoItem = Todoitem(name, isCompleted, id)
                        todoList.add(todoItem)
                    }
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
            val id = it
            val todoMap = mapOf(
                "name" to newTodo.name,
                "isCompleted" to newTodo.isCompleted
            )
            database.child(id).setValue(todoMap).addOnSuccessListener {
                // 성공 시 필요한 동작(예: 로그 출력)
            }.addOnFailureListener {
                // 실패 시 오류 처리
            }
        } ?: Log.e("AddItem", "Failed to generate a new key")
    }
    fun updateItem(id: String, name: String){
        val todoMap = mapOf(
            "name" to name
        )
        database.child(id).updateChildren(todoMap)
            .addOnSuccessListener {
                // 업데이트 성공 시 처리
            }
            .addOnFailureListener {
                // 업데이트 실패 시 처리
            }
    }
    fun deleteItem(todoItem: Todoitem) {
        val id = todoItem.id // 삭제할 데이터의 id
        if (id != null) {
            // 해당 id에 해당하는 데이터만 삭제합니다.
            database.child(id).removeValue()
                .addOnSuccessListener {
                    // 삭제 성공 시 처리
                }.addOnFailureListener {
                    // 삭제 실패 시 처리
                }
        }
    }
    fun toggleTodoComplete(todoItem: Todoitem) {
        todoItem.isCompleted = !todoItem.isCompleted

        // Firebase Realtime Database에서 해당 필드만 업데이트합니다.
        database.child("todoItems").child(todoItem.id).child("isCompleted").setValue(todoItem.isCompleted)
            .addOnSuccessListener {
                // 성공 처리
            }
            .addOnFailureListener {
                // 실패 처리
            }

        // UI를 업데이트하기 위해 LiveData를 업데이트합니다.
        items.value = items.value?.map { if (it.id == todoItem.id) todoItem else it }?.toMutableList()
        items.postValue(items.value)
    }
}