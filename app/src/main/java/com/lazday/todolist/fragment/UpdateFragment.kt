package com.lazday.todolist.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var task: TaskModel
    private lateinit var database: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        task = requireArguments().getSerializable("argument_task") as TaskModel
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTask.setText( task.task )
        binding.buttonDelete.setOnClickListener {
            Thread {
                database.delete( task )
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
    }
}