package com.example.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.Entities.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun Create(taskModel: TaskEntity)

    @Query("delete from Tasks where Id = :id")
    suspend fun Delete(id: Int)

    @Update
    suspend fun Update(taskModel: TaskEntity)

    @Query("select * from Tasks")
    fun GetAll() : LiveData<List<TaskEntity>>

    @Query("select * from Tasks where Id = :id")
    fun GetById(id : Int) : LiveData<TaskEntity?>

}