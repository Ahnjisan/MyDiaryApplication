package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private fun setRecyclerview(){
        val Todolist = this
        taskViewModel.items.observe(this){todoItems ->
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TodoItemAdapter(todoItems ?: emptyList(), Todolist)
            }
        }
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