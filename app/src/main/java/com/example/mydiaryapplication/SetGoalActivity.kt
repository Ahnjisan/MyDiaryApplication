package com.example.mydiaryapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapplication.databinding.ActivitySetGoalBinding

class SetGoalActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySetGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goalDescriptionEditText: EditText = binding.goalDescriptionEditText
        val monthSpinner: Spinner = binding.monthSpinner
        val saveGoalButton: Button = binding.saveGoalButton

        saveGoalButton.setOnClickListener {

            val goalDescription = goalDescriptionEditText.text.toString()
            val selectedMonth = monthSpinner.selectedItemPosition

            val intent = Intent(this, CalendarActivity::class.java).apply {
                putExtra("goalDescription", goalDescription)
                putExtra("selectedMonth", selectedMonth)
            }
            startActivity(intent)
        }
    }
}