package com.lazday.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tableTask WHERE completed=:completed")
    fun taskAll(completed: Boolean): LiveData<List<TaskModel>>

    @Query("SELECT * FROM tableTask WHERE completed=:completed AND date=:date")
    fun taskAll(completed: Boolean, date: Long): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskModel: TaskModel)

    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun delete(taskModel: TaskModel)

    @Query("DELETE FROM tableTask WHERE completed=1")
    fun deleteCompleted()

    @Query("DELETE FROM tableTask")
    fun deleteAll()
}