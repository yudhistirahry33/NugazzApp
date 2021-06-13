package com.lazday.todolist.fragment

import android.app.DatePickerDialog
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
import com.lazday.todolist.util.dateToDialog
import com.lazday.todolist.util.dateToLong
import com.lazday.todolist.util.dateToString
import com.lazday.todolist.util.dateToday

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var database: TaskDao
    private var dateSelected: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        dateSelected = dateToLong( dateToday()!! )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener(){
        binding.labelDate.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val date = dateToString(year, month, day)
                dateSelected = dateToLong( date!! )
                binding.textDate.text = date
            }
            dateToDialog(
                requireActivity(),
                datePicker
            )!!.show()
        }

        binding.buttonSave.setOnClickListener {
            val taskModel = TaskModel(
                id = 0,
                task = binding.editTask.text.toString(),
                completed = false,
                date = dateSelected!!
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