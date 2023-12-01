package com.example.mydiaryapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import android.widget.CalendarView
import android.widget.ImageButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var selectedDate: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }
        val imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, addlecture::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ButtonsFragment.newInstance(selectedDate))
                .commit()
        }
    }

    fun onFragmentButtonClicked(buttonId: Int) {
        when (buttonId) {
            R.id.btnAddEvent -> {
                // 일정 추가하기 화면으로 이동
                val intent = Intent(this, ScheduleActivity::class.java)
                val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                intent.putExtra("selectedDate", sdf.format(Date(selectedDate)))
                startActivity(intent)
            }
            R.id.btnTodolist -> {
                // Todo list 작성하기 화면으로 이동
                val intent = Intent(this, Todolist::class.java)
                val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                intent.putExtra("selectedDate", sdf.format(Date(selectedDate)))
                startActivity(intent)
            }
            R.id.btnDiary -> {
                // 일기 작성하기 화면으로 이동
                val intent = Intent(this, DiaryActivity::class.java)
                val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                intent.putExtra("selectedDate", sdf.format(Date(selectedDate)))
                startActivity(intent)
            }
            // 다른 버튼들에 대한 처리
        }
    }
}
