package com.example.mydiaryapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class ButtonsFragment : Fragment() {

    private var selectedDate: Long = 0

    companion object {
        private const val ARG_DATE = "selected_date"

        fun newInstance(date: Long) = ButtonsFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_DATE, date)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getLong(ARG_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buttons, container, false)

        view.findViewById<Button>(R.id.btnAddEvent).setOnClickListener {
            (activity as MainActivity).onFragmentButtonClicked(R.id.btnAddEvent)
        }
        view.findViewById<Button>(R.id.btnTodolist).setOnClickListener {
            (activity as MainActivity).onFragmentButtonClicked(R.id.btnTodolist)
        }
        view.findViewById<Button>(R.id.btnDiary).setOnClickListener {
            (activity as MainActivity).onFragmentButtonClicked(R.id.btnDiary)
        }
        view.findViewById<Button>(R.id.btnGoal).setOnClickListener {
            (activity as MainActivity).onFragmentButtonClicked(R.id.btnGoal)
        }
        // 다른 버튼들에 대한 리스너 설정

        return view
    }
}
