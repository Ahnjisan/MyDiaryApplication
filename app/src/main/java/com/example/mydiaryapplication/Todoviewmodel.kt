package com.example.mydiaryapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.util.Log



class Todoviewmodel: ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("todoItems")

    var items = MutableLiveData<MutableList<Todoitem>?>().apply {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newList = mutableListOf<Todoitem>()
                snapshot.children.mapNotNullTo(newList) { it.getValue(Todoitem::class.java) }
                value = newList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Todoviewmodel", "Data load cancelled: ${error.message}")
                // 에러 처리
            }
        })
    }
    fun addItem(newTodo: Todoitem){
        databaseReference.push().setValue(newTodo)
    }
    fun updateItem(id: String, name: String, text: String){
        val updateMap = mapOf<String, Any>(
            "name" to name,
            "desc" to text
        )
        databaseReference.child(id.toString()).updateChildren(updateMap)
    }
    fun deleteItem(todoItem: Todoitem) {
        // Firebase 데이터베이스에서 해당 항목 삭제
        databaseReference.child(todoItem.id).removeValue()

        // 로컬 리스트에서 항목 제거
        val list = items.value
        list?.remove(todoItem)
        items.postValue(list)
    }
    fun toggleTodoComplete(todoItem: Todoitem) {
        val updateMap = mapOf<String, Any>("isCompleted" to true)
        databaseReference.child(todoItem.id.toString()).updateChildren(updateMap)
    }
}