package com.example.diaryp

import android.app.Dialog
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class addlecture : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()

    data class Lecture(
        val prof: String? = null,
        val sub: String? = null,
        val time: String? = null,
        val weekdays: String? = null
    ) {
        // ArrayAdapter가 객체를 문자열로 변환할 수 있도록 toString 정의
        override fun toString(): String {
            return "$sub - $prof\n$time, $weekdays"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addlecture)

        val addLectureButton = findViewById<Button>(R.id.addlecture)
        addLectureButton.setOnClickListener {
            showLectureListDialog()
        }
    }

    private fun showLectureListDialog() {
        // 대화상자 생성
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.lecture_list_dialog)

        // ListView를 위한 어댑터 설정
        val listView = dialog.findViewById<ListView>(R.id.lectureListView)
        val lectures = mutableListOf<Lecture>()
        val adapter = ArrayAdapter<Lecture>(this, R.layout.lecture_item, lectures)
        listView.adapter = adapter

        // Firebase에서 데이터 불러오기
        val lectureRef = database.getReference("lectureinfo")
        lectureRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lectures.clear()
                dataSnapshot.children.forEach { lectureSnapshot ->
                    Log.d("addlecture", "Lecture Data: ${lectureSnapshot.value}")
                    try {
                        val lecture = lectureSnapshot.getValue(Lecture::class.java)
                        lecture?.let { lectures.add(it) }
                    } catch (e: Exception) {
                        Log.e("addlecture", "Error converting data to Lecture class", e)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
                Log.e("addlecture", "Database error: ${databaseError.toException()}")
            }
        })


// 무작위로 색상을 선택하기 위한 색상 배열 정의
        val colors = arrayOf(
            R.color.color1, R.color.color2, R.color.color3,
            R.color.color4, R.color.color5, R.color.color6, R.color.color7
        )
        // 선택된 항목을 시간표에 추가하는 리스너
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedLecture = lectures[position]
            val dayOfWeek = selectedLecture.weekdays?.lowercase(Locale.ROOT) ?: return@setOnItemClickListener
            val timeRange = selectedLecture.time?.split("-")?.map { it.trim() } ?: return@setOnItemClickListener
            val startHour = timeRange[0].split(":")[0].toIntOrNull() ?: return@setOnItemClickListener
            val endHour = timeRange[1].split(":")[0].toIntOrNull() ?: return@setOnItemClickListener
            val gridLayout = findViewById<GridLayout>(R.id.gridlayout_timetable)

            val rowSpan = endHour - startHour
            val firstCellId = resources.getIdentifier("${dayOfWeek}${startHour.toString().padStart(2, '0')}00", "id", packageName)
            val firstCell = findViewById<TextView>(firstCellId)

            firstCell?.let {
                val randomColorRes = colors.random()
                val randomColor = ContextCompat.getColor(this, randomColorRes)
                it.setBackgroundColor(randomColor)
                it.text = selectedLecture.sub

                // 새로운 LayoutParams 적용
                val layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, rowSpan, GridLayout.FILL),
                    GridLayout.spec(GridLayout.UNDEFINED, 1, GridLayout.FILL)
                ).apply {
                    setGravity(Gravity.FILL)
                }
                it.layoutParams = layoutParams
            }

            // GridLayout을 다시 그림
            gridLayout.invalidate()
            dialog.dismiss()
        }

        // 대화상자 크기 설정 및 표시
        dialog.show()
        val window = dialog.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.9).toInt(), (size.y * 0.5).toInt())
    }
}
