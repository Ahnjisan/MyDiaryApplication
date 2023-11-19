package com.example.mydiaryapplication

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiaryapplication.databinding.TodoItemCellBinding

class TodoItemViewHolder(
    private val context: Context,
    private val binding: TodoItemCellBinding,
    private val clickListener: TodoItemClickListener
): RecyclerView.ViewHolder(binding.root){
    fun bindTodoItem(todoItem: Todoitem)
    {
        binding.name.text = todoItem.name
        if(todoItem.isCompleted){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.completeButton.setImageResource(todoItem.imageResource())
        binding.completeButton.setColorFilter(todoItem.imageColor(context))

        binding.completeButton.setOnClickListener{
            clickListener.completeTodoItem(todoItem)
        }
        binding.todoCellContainer.setOnClickListener {
            clickListener.completeTodoItem(todoItem)
        }
    }
}