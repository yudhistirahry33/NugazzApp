package com.lazday.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableTask")
data class TaskModel (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val task: String,
    val completed: Boolean,
    val date: Long
)