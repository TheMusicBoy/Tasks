package com.example.domain

import androidx.lifecycle.LiveData

interface ITaskRepository {

    fun Create(task: Task)

    fun Delete(id : Int)

    fun Update(task : Task)

    fun GetAll() : LiveData<List<Task>>

    fun GetById(id : Int) : LiveData<Task?>

}