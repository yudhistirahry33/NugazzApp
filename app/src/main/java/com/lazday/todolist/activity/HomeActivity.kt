package com.lazday.todolist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazday.todolist.R

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}