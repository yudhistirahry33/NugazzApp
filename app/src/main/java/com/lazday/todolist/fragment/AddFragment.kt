package com.lazday.todolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var database: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            val taskModel = TaskModel(
                id = 0,
                task = binding.editTask.text.toString(),
                completed = false,
                date = System.currentTimeMillis()
            )
            addTask( taskModel )
        }
    }

    private fun addTask(taskModel: TaskModel){
        Thread {
            database.insert( taskModel )
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireActivity(),
                    "Berhasil ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        }.start()
    }
}