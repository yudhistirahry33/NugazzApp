package com.lazday.todolist.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lazday.todolist.R
import com.lazday.todolist.activity.AllActivity
import com.lazday.todolist.activity.UpdateActivity
import com.lazday.todolist.adapter.TaskAdapter
import com.lazday.todolist.adapter.TaskCompletedAdapter
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentHomeBinding
import com.lazday.todolist.util.dateToLong
import com.lazday.todolist.util.dateToday

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
    }

    override fun onStart() {
        super.onStart()
        setupData()
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
                startActivity(
                    Intent(requireActivity(), UpdateActivity::class.java)
                        .putExtra("intent_task", taskModel )
                )
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
        binding.textAll.setOnClickListener {
            startActivity(Intent(requireActivity(), AllActivity::class.java))
        }
        binding.textCompleted.setOnClickListener {
            when (binding.listTaskCompleted.visibility) {
                View.VISIBLE -> {
                    binding.listTaskCompleted.visibility = View.GONE
                    binding.imageCompleted.setImageResource(R.drawable.ic_arrow_right)
                }
                View.GONE -> {
                    binding.listTaskCompleted.visibility = View.VISIBLE
                    binding.imageCompleted.setImageResource(R.drawable.ic_arrow_down)
                }
            }
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun setupData(){
        binding.textToday.text = dateToday()
        database.taskAll( false, dateToLong( dateToday()!! )!! ).observe(viewLifecycleOwner, Observer {
            adapterTask.addList( it )
            binding.textAlert.apply {
                text = "Tidak ada tugas hari ini"
                visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        })
        database.taskAll( true, dateToLong( dateToday()!! )!! ).observe(viewLifecycleOwner, Observer {
            adapterTaskCompleted.addList( it )
            val visibleCompleted = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.textCompleted.visibility = visibleCompleted
            binding.imageCompleted.visibility = visibleCompleted
        })
    }
}