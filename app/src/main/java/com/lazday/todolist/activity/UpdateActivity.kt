package com.lazday.todolist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazday.todolist.databinding.ActivityUpdateBinding

class UpdateActivity : BaseActivity() {
    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}