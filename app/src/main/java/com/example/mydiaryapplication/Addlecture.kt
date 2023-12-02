package com.example.mydiaryapplication

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class addlecture : AppCompatActivity() {
    // Firebase Database 인스턴스와 선택된 강의 목록을 관리하기 위한 변수 선언
    private val database = FirebaseDatabase.getInstance()
    private val selectedLectures = mutableListOf<Lecture>()
    // 강의 정보를 저장하기 위한 데이터 클래스 정의
    data class Lecture(
        val prof: String? = null,
        val sub: String? = null,
        val time: String? = null,
        val weekdays: String? = null
    ) {
        // Lecture 객체를 문자열로 변환하는 함수
        override fun toString(): String {
            return "$sub - $prof\n$time, $weekdays"
        }
    }
    // 액티비티 생성 시 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addlecture)
        // 강의 추가 버튼 설정 및 클릭 리스너 할당
        val addLectureButton = findViewById<Button>(R.id.addlecture)
        addLectureButton.setOnClickListener {
            showLectureListDialog()
        }

        val imageView = findViewById<ImageView>(R.id.backtohome4)
        imageView.setOnClickListener {
            finish()
        }
        loadSelectedLecturesFromFirebase() // Firebase에서 선택된 강의 목록 로드
    }
    // Firebase에서 선택된 강의 목록을 불러오는 함수
    private fun loadSelectedLecturesFromFirebase() {
        val lectureListRef = database.getReference("lecturelist")
        lectureListRef.addListenerForSingleValueEvent(object : ValueEventListener {
            // 데이터가 변경될 때 호출되는 함수
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                selectedLectures.clear() // 기존 목록 초기화
                dataSnapshot.children.forEach { lectureSnapshot ->
                    // 강의 데이터를 Lecture 객체로 변환하여 목록에 추가
                    val lecture = lectureSnapshot.getValue(Lecture::class.java)
                    lecture?.let { selectedLectures.add(it) }
                }
                displaySelectedLectures() // 불러온 강의 목록을 화면에 표시
            }
            // 데이터 로드 실패 시 호출되는 함수
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("addlecture", "DB 오류: ${databaseError.toException()}")
            }
        })
    }
    // 강의 목록을 표시하는 다이얼로그를 보여주는 함수
    private fun showLectureListDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.lecture_list_dialog)
        // 다이얼로그 내의 리스트뷰 설정
        val listView = dialog.findViewById<ListView>(R.id.lectureListView)
        val lectures = mutableListOf<Lecture>()
        val adapter = ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, lectures)
        listView.adapter = adapter
        // Firebase에서 강의 정보를 불러오는 로직
        val lectureRef = database.getReference("lectureinfo")
        lectureRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lectures.clear() // 기존 목록 초기화
                dataSnapshot.children.forEach { lectureSnapshot ->
                    val lecture = lectureSnapshot.getValue(Lecture::class.java)
                    lecture?.let { lectures.add(it) } // 강의 목록에 추가
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("addlecture", "DB 오류: ${databaseError.toException()}")
            }
        })
        // 리스트 아이템 클릭 이벤트 처리
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedLecture = lectures[position] // 선택된 강의
            // 이미 선택된 강의는 추가하지 않음
            if (!selectedLectures.any { it.sub == selectedLecture.sub && it.time == selectedLecture.time && it.weekdays == selectedLecture.weekdays }) {
                selectedLectures.add(selectedLecture)
                displaySelectedLectures()
            } else {
                Toast.makeText(this, "이미 추가된 강의입니다.", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss() //다이얼로그 종료
        }

        dialog.show()
        val window = dialog.window
        window?.setLayout((resources.displayMetrics.widthPixels * 0.9).toInt(),
            (resources.displayMetrics.heightPixels * 0.7).toInt())
    }
    // 강의 요일을 숫자로 변환
    private fun Lecture.weekdayToInt(): Int {
        return when(this.weekdays?.lowercase()) {
            "mon" -> 1
            "tue" -> 2
            "wed" -> 3
            "thu" -> 4
            "fri" -> 5
            else -> 6
        }
    }
    // 선택된 강의 목록을 화면에 표시하고 Firebase에 저장하는 함수
    private fun displaySelectedLectures() {
        // 요일별로 먼저 정렬하고, 같은 요일 내에서 시간 순으로 정렬
        selectedLectures.sortWith(compareBy({ it.weekdayToInt() }, { it.time }))
        // 정렬된 강의 목록을 문자열로 변환하여 텍스트뷰에 표시
        val displayText = selectedLectures.joinToString("\n\n") { it.toString() }
        val lecturesTextView = findViewById<TextView>(R.id.selectedLecturesTextView)
        lecturesTextView.text = displayText

        saveSelectedLecturesToFirebase()
    }
    // 선택된 강의 목록을 Firebase에 저장하는 함수
    private fun saveSelectedLecturesToFirebase() {
        val lectureListRef = database.getReference("lecturelist")
        lectureListRef.setValue(selectedLectures)
            .addOnSuccessListener {
                Log.d("addlecture", "선택된 lecture 성공적으로 저장.")
            }
            .addOnFailureListener { e ->
                Log.e("addlecture", "저장 실패: ", e)
            }
    }
}


