package com.lazday.todolist.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.lazday.todolist.R
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.FragmentDetailBinding
import com.lazday.todolist.util.dateToString

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var task: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        task = requireActivity().intent.getSerializableExtra("intent_task") as TaskModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupData()
    }

    private fun setupListener(){
        binding.imageEdit.setOnClickListener {
            findNavController()
                .navigate(R.id.action_detailFragment_to_updateFragment, bundleOf("argument_task" to task))
        }
    }

    private fun setupData(){
        binding.textTask.text = task.task
        binding.textDate.text = dateToString( task.date )
    }
}