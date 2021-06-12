package com.lazday.todolist.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lazday.todolist.R
import com.lazday.todolist.adapter.TaskAdapter
import com.lazday.todolist.adapter.TaskCompletedAdapter
import com.lazday.todolist.database.DatabaseClient
import com.lazday.todolist.database.TaskDao
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentAllBinding

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var database: TaskDao
    private lateinit var adapterTask: TaskAdapter
    private lateinit var adapterTaskCompleted: TaskCompletedAdapter
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupListener()
        database.taskAll( false ).observe(viewLifecycleOwner, Observer {
            adapterTask.addList( it )
            binding.textAlert.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
        database.taskAll( true ).observe(viewLifecycleOwner, Observer {
            adapterTaskCompleted.addList( it )
            val visibleCompleted = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.textCompleted.visibility = visibleCompleted
            binding.imageCompleted.visibility = visibleCompleted
        })
    }

    private fun setupList() {
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
        binding.imageMenu.setOnClickListener {
            PopupMenu(requireActivity(), it).apply {
                setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.action_new -> {
                            findNavController().navigate(R.id.action_allFragment_to_addFragment)
                            true
                        }
                        R.id.action_delete -> {
                            Thread { database.deleteCompleted() }.start()
                            true
                        }
                        R.id.action_delete_all -> {
                            Thread { database.deleteAll() }.start()
                            true
                        }
                        else -> false
                    }
                }
                inflate(R.menu.menu_task)
                show()
            }
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
    }
}