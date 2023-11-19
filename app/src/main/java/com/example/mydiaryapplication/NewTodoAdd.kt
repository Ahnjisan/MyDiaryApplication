package com.example.mydiaryapplication

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mydiaryapplication.databinding.FragmentNewTodoAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTodoAdd(var items: Todoitem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTodoAddBinding
    private lateinit var taskViewModel: Todoviewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(items !=null){
            binding.todoTitle.text = "Edit Todo"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(items!!.name)
            binding.desc.text = editable.newEditable(items!!.desc)

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

    private fun saveAction(){
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        if(items == null){
            val newTodo = Todoitem(name, desc)
            taskViewModel.addItem(newTodo)
        }
        else{
            taskViewModel.updateItem(items!!.id, name, desc)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()


    }

}