package com.example.mydiaryapplication

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mydiaryapplication.databinding.FragmentNewTodoAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTodoAdd(var items: Todoitem?, val selectedDate: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTodoAddBinding
    private lateinit var taskViewModel: Todoviewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(items !=null){
            binding.todoTitle.text = "Edit Todo"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(items!!.name)

        }
        taskViewModel = ViewModelProvider(activity).get(Todoviewmodel::class.java)
        binding.saveButton.setOnClickListener{
            saveAction()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTodoAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
            if (items == null) {
                val newTodo = Todoitem(name = name, selectedDate = selectedDate)
                taskViewModel.addItem(newTodo)
            } else {
                items!!.name = name
                // 필요한 경우 다른 속성 업데이트 로직 추가
                taskViewModel.updateItem(items!!.id, name)
            }
        dismiss()
    }


}