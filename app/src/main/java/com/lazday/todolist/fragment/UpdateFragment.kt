package com.lazday.todolist.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentUpdateBinding
import com.lazday.todolist.util.dateToDialog
import com.lazday.todolist.util.dateToLong
import com.lazday.todolist.util.dateToString

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var data: TaskModel
    private lateinit var database: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        data = requireArguments().getSerializable("argument_task") as TaskModel
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupData()
    }

    private fun setupListener(){
        binding.labelDate.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.textDate.text = dateToString(year, month, day)
            }
            dateToDialog(
                requireActivity(),
                datePicker
            )!!.show()
        }
        binding.buttonSave.setOnClickListener {

            data.task = binding.editTask.text.toString()
            data.date = dateToLong( binding.textDate.text.toString() ) !!

            Thread {
                database.update( data )
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Perubahan disimpan", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
        binding.buttonDelete.setOnClickListener {
            Thread {
                database.delete( data )
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
    }

    private fun setupData(){
        binding.editTask.setText( data.task )
        binding.textDate.text = dateToString( data.date )
    }
}