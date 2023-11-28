package com.example.diaryp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryp.databinding.ViewscheduleBinding

//일정 정보 저장-> 리사이클러뷰에 표시
data class Schedule(
    var id: String = "",
    var title: String = "",
    var memo: String = "",
    var hour: Int = 0,
    var minute: Int = 0
)

class ScheduleAdapter(
    private val scheduleList: MutableList<Schedule>,
    private val onDeleteClick: (Schedule) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    //scheduleviewholder-> 스케줄 뷰
    class ScheduleViewHolder(val binding: ViewscheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ViewscheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    //삭제 버튼 누를 때 onDeleteClick 호출
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]
        with(holder.binding) {
            title.text = schedule.title
            memo.text = schedule.memo
            selectedTime.text = formatTime(schedule.hour, schedule.minute)
            trash.setOnClickListener { onDeleteClick(schedule) }
        }
    }

    override fun getItemCount() = scheduleList.size

    //HH:mm 형식
    private fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}


