package com.example.mydiaryapplication

data class Diary(
    var date: String = "",
    var title: String = "",
    var content: String = "",
    var imageUrl: String? = null // 이미지 URL
)
