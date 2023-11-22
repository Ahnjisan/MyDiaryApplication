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
        updateTodoItemAppearance(todoItem)

        binding.completeButton.setOnClickListener {
            clickListener.completeTodoItem(todoItem)
            // 상태 변경 후 UI 업데이트를 위해 호출
            updateTodoItemAppearance(todoItem)
        }

        binding.todoCellContainer.setOnClickListener {
            // 여기에서 항목 편집 동작을 호출
            clickListener.editTodoItem(todoItem)
        }

        binding.deleteButton.setOnClickListener {
            clickListener.deleteTodoItem(todoItem)
        }
    }

    private fun updateTodoItemAppearance(todoItem: Todoitem) {
        if (todoItem.isCompleted) {
            binding.name.paintFlags = binding.name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.completeButton.setImageResource(R.drawable.checked_24)
        } else {
            binding.name.paintFlags = binding.name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.completeButton.setImageResource(R.drawable.unchecked_24)
        }
    }
}