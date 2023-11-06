package com.example.mydiaryapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiaryapplication.databinding.ListItemsBinding

class ItemAdapter(val items: Array<Item>)
    : RecyclerView.Adapter<ItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(private val binding: ListItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Item) {
            binding.imageView.setImageResource(when (items.item) {
                EItem.SCHEDULE -> R.drawable.schedule
                EItem.CHECKLIST -> R.drawable.checklist
                EItem.PENCIL -> R.drawable.pencil
                EItem.REWARD -> R.drawable.reward
            })
            binding.txtItem.text = items.name
        }
    }

}