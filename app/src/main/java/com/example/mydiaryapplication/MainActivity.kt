package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydiaryapplication.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mydiaryapplication.databinding.ActivityMainBinding
import com.example.mydiaryapplication.Home
import com.example.mydiaryapplication.Schedule
import com.example.mydiaryapplication.Todolist
import com.example.mydiaryapplication.Diary
import com.example.mydiaryapplication.TimetableFragment
import com.example.mydiaryapplication.GoalsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.calendar -> replaceFragment(Home())
                R.id.schedule -> replaceFragment(Schedule())
                R.id.list -> replaceFragment(Todolist())
                R.id.diary -> replaceFragment(Diary())
                R.id.time -> replaceFragment(TimetableFragment())
                R.id.goals -> replaceFragment(GoalsFragment())
                else -> {

                }
            }
            true
        }
        replaceFragment(Home())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}