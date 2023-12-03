package com.example.mydiaryapplication

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mydiaryapplication.databinding.ActivityCalendarBinding
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import java.util.Calendar

@Suppress("DEPRECATION")
class CalendarActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private val clickedDays = mutableMapOf<String, Int>()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton: Button = binding.backButton

        backButton.setOnClickListener {
            onBackPressed()
        }

        val goalDescription = intent.getStringExtra("goalDescription")
        val selectedMonth = intent.getIntExtra("selectedMonth", Calendar.JANUARY)

        binding.goalDescription.text = goalDescription

        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDayClick(eventDay: EventDay) {
                handleDateClick(eventDay.calendar)
            }
        })

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, selectedMonth)
        binding.calendarView.setDate(calendar)

        restoreState()

        updateEvents()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleDateClick(calendar: Calendar) {
        val key = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
        val clickCount = when(clickedDays.getOrDefault(key, 0)) {
            0 -> 50
            50 -> 100
            else -> 100
        }
        clickedDays[key] = clickCount

        saveState().addOnSuccessListener{
            updateEvents()
        }
    }

    private fun drawable(isCompleted: Boolean): Drawable? {
        val colorResId = if (isCompleted) R.color.blue else R.color.colorPrimary
        return ContextCompat.getDrawable(this, colorResId)
    }

    private fun updateEvents() {
        val events = clickedDays.map { (key, clickCount) ->
            val parts = key.split("-")
            val year = parts[0].toInt()
            val month = parts[1].toInt() - 1
            val dayOfMonth = parts[2].toInt()
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val isCompleted = clickCount >= 100
            EventDay(calendar, drawable(isCompleted)!!)
        }
        binding.calendarView.setEvents(events)
    }

    private fun saveState(): Task<Void> {
        val goalDescription = binding.goalDescription.text.toString()
        val selectedMonth = intent.getIntExtra("selectedMonth", Calendar.JANUARY)
        val ref = database.getReference("goals")


        val monthKey = "${Calendar.getInstance().get(Calendar.YEAR)}-${selectedMonth + 1}"
        val monthData = mutableMapOf<String, Any>()
        monthData["goal"] = goalDescription ?: ""
        clickedDays.forEach { (key, value) ->
            monthData[key] = value
        }

        return ref.child(monthKey).updateChildren(monthData)

    }

    private fun restoreState() {
        val selectedMonth = intent.getIntExtra("selectedMonth", Calendar.JANUARY)
        val ref = database.getReference("goals")

        ref.child("${Calendar.getInstance().get(Calendar.YEAR)}-${selectedMonth + 1}").get().addOnSuccessListener { dataSnapshot ->
            val data = dataSnapshot.getValue<Map<String, Any>>()
            if (data != null) {
                clickedDays.clear()
                for ((key, value) in data) {
                    if (key == "goal" && value is String) {
                        binding.goalDescription.text = value
                    } else if (value is Int) {
                        clickedDays[key] = value
                    }
                }
                updateEvents()
            }
        }
    }
}