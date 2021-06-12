package com.lazday.todolist.activity

import android.os.Bundle
import com.lazday.todolist.databinding.ActivityAllBinding

class AllActivity : BaseActivity() {
    private val binding by lazy { ActivityAllBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}