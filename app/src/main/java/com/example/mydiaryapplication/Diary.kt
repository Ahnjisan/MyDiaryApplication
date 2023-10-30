package com.example.mydiaryapplication

enum class Icon {
    GOAL,
    LIST,
    NOTE,
    SCHEDULE
}

data class Diary(val s: String, val goal: Icon) {
    val item: String = ""
    val icon: Icon
        get() {
            TODO()
        }
}