package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydiaryapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val items = arrayOf(
        Diary("일정 추가하기", Icon.GOAL),
        Diary("Todo list 작성하기", Icon.LIST),
        Diary("일기 작성하기", Icon.NOTE),
        Diary("이달의 목표 실천하기", Icon.SCHEDULE),
    )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.diaryItem.layoutManager = LinearLayoutManager(this)
        binding.diaryItem.adapter = DiaryAdapter(items)
        // 안지산 주석
    }
}