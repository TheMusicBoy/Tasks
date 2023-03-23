package com.example.domain

import androidx.lifecycle.LiveData
import java.util.*

interface ITaskRepository {
    fun Create(task: Task)

    fun Delete(id : UUID)

    fun Update(task : Task)

    fun GetAll() : LiveData<List<Task>>

    fun GetById(id : UUID) : LiveData<Task?>
}