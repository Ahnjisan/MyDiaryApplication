package com.example.mydiaryapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import android.widget.CalendarView

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 캘린더 뷰 초기화
        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 날짜가 선택될 때 수행할 로직
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            handleDateSelection(selectedDate)
        }

        // 바텀 네비게이션 뷰 초기화
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_schedule -> {
                    val intent = Intent(this, ScheduleActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_todo -> {
                    val intent = Intent(this, Todolist::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_diary -> {
                    val intent = Intent(this, DiaryActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_timetable -> {
                    val intent = Intent(this, addlecture::class.java)
                    startActivity(intent)
                    // 시간표 로직
                    true
                }
                else -> false
            }
        }
    }

    private fun handleDateSelection(selectedDate: Calendar) {
        // 선택된 날짜 처리 로직
        // 예: 선택된 날짜를 다른 액티비티나 프래그먼트에 전달
    }
}