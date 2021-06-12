package com.lazday.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tableTask")
data class TaskModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var task: String,
    var completed: Boolean,
    var date: Long
) : Serializable