package com.example.mydiaryapplication

enum class EItem {
    CHECKLIST,
    PENCIL,
    REWARD,
    SCHEDULE
}
data class Item(
    val name: String,
    val item: EItem
)