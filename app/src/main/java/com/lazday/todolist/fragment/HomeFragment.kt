package com.lazday.todolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lazday.todolist.R
import com.lazday.todolist.adapter.TaskAdapter
import com.lazday.todolist.adapter.TaskCompletedAdapter
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TaskDao
    private lateinit var adapterTask: TaskAdapter
    private lateinit var adapterTaskCompleted: TaskCompletedAdapter
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupListener()
        database.taskAll( false ).observe(viewLifecycleOwner, Observer {
            adapterTask.addList( it )
        })
        database.taskAll( true ).observe(viewLifecycleOwner, Observer {
            adapterTaskCompleted.addList( it )
        })
    }

    private fun setupList(){
        adapterTask = TaskAdapter(arrayListOf(), object: TaskAdapter.AdapterListener {
            override fun onCompleted(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.completed = true
                Thread {
                    database.update(taskSelected)
                }.start()
            }
            override fun onDetail(taskModel: TaskModel) {

            }
        })
        binding.listTask.adapter = adapterTask
        adapterTaskCompleted = TaskCompletedAdapter(arrayListOf(), object: TaskCompletedAdapter.AdapterListener {
            override fun onClick(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.completed = false
                Thread {
                    database.update(taskSelected)
                }.start()
            }
        })
        binding.listTaskCompleted.adapter = adapterTaskCompleted
    }

    private fun setupListener(){
        binding.textCompleted.setOnClickListener {
            binding.listTaskCompleted.apply {
                visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }
}