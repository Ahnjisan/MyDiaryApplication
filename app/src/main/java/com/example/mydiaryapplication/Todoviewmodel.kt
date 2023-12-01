package com.example.mydiaryapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import java.util.UUID


class Todoviewmodel(private val selectedDate: String): ViewModel() {
    var items = MutableLiveData<MutableList<Todoitem>?>()
    private val database = FirebaseDatabase.getInstance().getReference("todoItems")

    init {
        loadTodoItems(selectedDate)
    }
    private fun loadTodoItems(selectedDate: String) {
        database.child(selectedDate).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList = mutableListOf<Todoitem>()
                snapshot.children.forEach { data ->
                    val id = data.key
                    val name = data.child("name").getValue(String::class.java)
                    val isCompleted = data.child("isCompleted").getValue(Boolean::class.java) ?: false // 기본값으로 false를 사용하거나 필요에 따라 다른 기본값 사용


                    if (id != null && name != null) {
                        val todoItem = Todoitem(name = name, isCompleted = isCompleted, id = id)
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
        newTodo.selectedDate?.let { selectedDate ->
            val newTodoKey = database.child(selectedDate).push().key
            newTodoKey?.let {
                val id = it
                val todoMap = mapOf(
                    "name" to newTodo.name,
                    "isCompleted" to newTodo.isCompleted
                )
                database.child(selectedDate).child(id).setValue(todoMap)
                // 추가 처리
            }
        }
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
            database.child(selectedDate).child(id).removeValue()
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
        todoItem.selectedDate?.let { selectedDate ->
            todoItem.id?.let { id ->
                database.child(selectedDate).child(id).child("isCompleted").setValue(todoItem.isCompleted)
                    .addOnSuccessListener {
                        // 성공 처리
                    }
                    .addOnFailureListener {
                        // 실패 처리
                    }
            }
        }

        // LiveData 업데이트
        items.value = items.value?.map { if (it.id == todoItem.id) todoItem else it }?.toMutableList()
        items.postValue(items.value)
    }
}
class TodoviewmodelFactory(private val selectedDate: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Todoviewmodel::class.java)) {
            return Todoviewmodel(selectedDate) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}