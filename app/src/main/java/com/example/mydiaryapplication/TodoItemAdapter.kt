package com.example.mydiaryapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiaryapplication.databinding.TodoItemCellBinding

class TodoItemAdapter(
    private val todoItems: List<Todoitem>,
    private val clickListener: TodoItemClickListener

): RecyclerView.Adapter<TodoItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TodoItemCellBinding.inflate(from, parent, false)
        return TodoItemViewHolder(parent.context, binding, clickListener)
    }
    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindTodoItem(todoItems[position])
    }
}