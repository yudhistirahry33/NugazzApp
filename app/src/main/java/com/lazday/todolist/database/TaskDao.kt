package com.lazday.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tableTask WHERE completed=:completed")
    fun taskAll(completed: Boolean): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(taskModel: TaskModel)

    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun remove(taskModel: TaskModel)
}