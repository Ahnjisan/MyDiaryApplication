package com.example.mydiaryapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // 현재 시간을 밀리초 단위로 저장하는 변수
    private var selectedDate: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 캘린더뷰에서 선택한 날짜를 selectedDate에 저장
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }
        // 시간표 버튼 누르면 시간표 관련 페이지로 넘어감
        val imageButton = findViewById<ImageView>(R.id.lecture)
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

    // Fragment에 버튼이 클릭했을 때 호출되고 그에 맞는 페이지로 이동
    fun onFragmentButtonClicked(buttonId: Int) {
        when (buttonId) {
            R.id.btnAddEvent -> {
                // 일정 추가하기 화면으로 이동
                val intent = Intent(this, ScheduleActivity::class.java)
                // SimpleDateFormat을 사용하여 선택된 날짜(selectedDate)를 "yyyyMMdd" 형식의 문자열로 변환
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
            R.id.btnGoal -> {
                // 이 달의 목표 화면으로 이동
                val intent = Intent(this, SetGoalActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
