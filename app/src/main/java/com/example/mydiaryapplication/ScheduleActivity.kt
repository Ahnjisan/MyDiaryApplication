package com.example.mydiaryapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapplication.Schedule
import com.example.mydiaryapplication.databinding.AddscheduleBinding
import com.example.mydiaryapplication.ScheduleAdapter
import com.example.mydiaryapplication.databinding.ActivityScheduleBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class ScheduleActivity : AppCompatActivity() {

    // Firebase Realtime Database에 연결
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("schedules")
    private lateinit var mainBinding: ActivityScheduleBinding
    private lateinit var scheduleAdapter: ScheduleAdapter //리사이클러 뷰에 일정 표시
    private val scheduleList: MutableList<Schedule> = mutableListOf() //mutable- 일정 추가, 삭제
    //초기값으로 0 할당 (기본값 설정)
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        listenForChangesInFirebase()
        setupRecyclerView()
        //일정 추가버튼 누르면 showaddscheduledialog 메서드 호출
        mainBinding.addscheduleF.setOnClickListener {
            showAddScheduleDialog()
        }

        //메인페이지로 이동
        val imageView = findViewById<ImageView>(R.id.backtohome4)
        imageView.setOnClickListener {
            finish()
        }
    }
    // 스케줄 목록을 가져오기 위해 Firebase를 청취합니다.
    private fun listenForChangesInFirebase() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                scheduleList.clear()
                for (scheduleSnapshot in dataSnapshot.children) {
                    val schedule = scheduleSnapshot.getValue(Schedule::class.java)
                    if (schedule != null) {
                        scheduleList.add(schedule)
                    }
                }
                scheduleAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스 오류를 처리합니다.
            }
        })
    }

    //일정 추가하기
    private fun showAddScheduleDialog() {
        val dialogBinding = AddscheduleBinding.inflate(layoutInflater)
        val dialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
        }
        //시간 설정 버튼 클릭 시 showtimepicker 메소드 호출
        dialogBinding.time.setOnClickListener {
            showTimePicker()
        }
        //창 닫는 버튼 클릭 시 dismiss 메소드 호출되면서 다이얼로그 닫힘
        dialogBinding.close.setOnClickListener {
            dialog.dismiss()
        }

        //저장 버튼
        dialogBinding.saveschedule.setOnClickListener {
            //입력한 제목과 메모 가져옴
            val title = dialogBinding.edscheduletitle1.text.toString()
            val memo = dialogBinding.edscheduletitle2.text.toString()
            //새로운 스케쥴 객체 생성
            val newSchedule = Schedule(UUID.randomUUID().toString(), title, memo, selectedHour, selectedMinute)
            //새로운 스케줄 리스트에 추가
            addScheduleToList(newSchedule)
            //저장하면 다이얼로그 닫힘
            dialog.dismiss()
            addScheduleToFirebase(newSchedule)
        }
        //다이얼로그 화면에 표시
        dialog.show()
    }

    //시간 설정하기
    private fun showTimePicker() {
        val calendar = Calendar.getInstance() //현재 날짜, 시간를 갖는 calendar 객체 생성 (타임 피커 초기값 제공)
        val timePickerDialog = TimePickerDialog(
            this, //현재 액티비티, 입력한 시간 설정
            { _, hour, minute ->
                selectedHour = hour
                selectedMinute = minute
            },
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            true //24시간 형식
        )
        timePickerDialog.show() //화면에 표시
    }

    //setuoRecyclerView 매서드 호출-> recyclerview 설정
    private fun setupRecyclerView() {
        scheduleAdapter = ScheduleAdapter(scheduleList, this::onDeleteSchedule)
        mainBinding.schedulerv.adapter = scheduleAdapter
    }
    //일정을 schedulelist에 추가 후 어댑터에 변경 알림
    private fun addScheduleToList(schedule: Schedule) {
        scheduleList.add(schedule)
        scheduleAdapter.notifyDataSetChanged()
    }

    // 새로운 스케줄을 Firebase에 추가합니다.
    private fun addScheduleToFirebase(schedule: Schedule) {
        myRef.child(schedule.id).setValue(schedule)
    }

    //일정을 schedulelist에서 제거 후 어댑터에 변경 알림
    private fun onDeleteSchedule(schedule: Schedule) {
        scheduleList.remove(schedule)
        scheduleAdapter.notifyDataSetChanged()
        deleteScheduleFromFirebase(schedule)
    }
    // Firebase에서 스케줄을 삭제합니다.
    private fun deleteScheduleFromFirebase(schedule: Schedule) {
        myRef.child(schedule.id).removeValue()
    }

}