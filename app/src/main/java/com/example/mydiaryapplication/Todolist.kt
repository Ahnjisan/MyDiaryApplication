package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydiaryapplication.databinding.ActivityTodolistBinding
import com.google.firebase.FirebaseApp

class Todolist : AppCompatActivity(), TodoItemClickListener {

    private lateinit var binding: ActivityTodolistBinding
    private lateinit var taskViewModel: Todoviewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel = ViewModelProvider(this).get(Todoviewmodel::class.java)
        binding.todolistadd.setOnClickListener {
            NewTodoAdd(null).show(supportFragmentManager, "newTodotag")
        }
        setRecyclerview()

    }
    private fun setRecyclerview() {
        val initialTodoItems = taskViewModel.items.value ?: emptyList()
        val adapter = TodoItemAdapter(initialTodoItems, this)
        binding.todoListRecyclerView.adapter = adapter
        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.items.observe(this, Observer { todoItems ->
            adapter.updateData(todoItems ?: emptyList())
        })
    }

    override fun editTodoItem(todoItem: Todoitem) {
        NewTodoAdd(todoItem).show(supportFragmentManager,"newTodoTag")
    }

    override fun completeTodoItem(todoItem: Todoitem) {
        taskViewModel.toggleTodoComplete(todoItem)
    }
    override fun deleteTodoItem(todoItem: Todoitem) {
        taskViewModel.deleteItem(todoItem)
    }

}