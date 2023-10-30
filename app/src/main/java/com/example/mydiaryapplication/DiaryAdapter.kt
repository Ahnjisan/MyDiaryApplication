package com.example.mydiaryapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiaryapplication.databinding.ActivityMainBinding
import com.example.mydiaryapplication.databinding.ActivitySubDiaryBinding

class DiaryAdapter(val items: Array<Diary>)
    : RecyclerView.Adapter<DiaryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivitySubDiaryBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    class Holder(private val binding: ActivitySubDiaryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Diary) {
            binding.imageView.setImageResource( when (items.icon) {
                Icon.GOAL -> R.drawable.goal
                Icon.LIST -> R.drawable.list
                Icon.NOTE -> R.drawable.note
                Icon.SCHEDULE -> R.drawable.schedule
            })
            binding.txtItem.text = items.item
        }
    }

}