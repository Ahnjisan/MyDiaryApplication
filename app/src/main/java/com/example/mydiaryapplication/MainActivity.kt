package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydiaryapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val items = arrayOf(
        Item("일정 추가하기", EItem.SCHEDULE),
        Item("Todo list 작성하기", EItem.CHECKLIST),
        Item("일기 작성하기", EItem.PENCIL),
        Item("이달의 목표 실천하기", EItem.REWARD)
    )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recMenu.layoutManager = LinearLayoutManager(this)
        binding.recMenu.adapter = ItemAdapter(items)

        binding.calendarView.setOnDateChangeListener{view, year, month, dayOfMonth->

        }

    }
}