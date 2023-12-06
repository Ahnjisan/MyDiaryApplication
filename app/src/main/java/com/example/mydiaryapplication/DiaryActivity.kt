package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DiaryActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    // selectedDate가 String타입이기 떄문에 MainActivity에서 string으로 변환
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        // Firebase에 상위노드 이름 diaries로 저장
        database = FirebaseDatabase.getInstance().getReference("diaries")
        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)
        selectedDate = intent.getStringExtra("selectedDate") ?: ""
        if (selectedDate.isNotEmpty()){
            loadDiaryData(selectedDate)
        }

        //메인페이지로 이동
        val imageView = findViewById<ImageView>(R.id.backtohome4)
        imageView.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            // 이미지가 업로드된 후에만 일기를 저장
                saveDiary()
        }
    }

    // 날짜로 값을 받으면 그 값에 맞는 firebase 자식노드에 접근
    private fun loadDiaryData(date: String?) {
        date?.let {
            database.child(date).addListenerForSingleValueEvent(object : ValueEventListener {
                // 데이터가 성공적으로 로드되었을 때
                override fun onDataChange(snapshot: DataSnapshot) {
                    val diary = snapshot.getValue(Diary::class.java)
                    diary?.let {
                        titleEditText.setText(diary.title)
                        contentEditText.setText(diary.content)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
        }
    }


    private fun saveDiary() {
        val diary = Diary(
            date = selectedDate ?: "",
            title = titleEditText.text.toString(),
            content = contentEditText.text.toString(),
        )
        // selectedDate 자식 노드에 diary 객체를 저장
        database.child(selectedDate).setValue(diary).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Diary saved successfully.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save diary.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
