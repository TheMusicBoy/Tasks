package com.example.danilaproject.TaskListFragment

import androidx.lifecycle.ViewModel
import com.example.data.TaskRepository
import com.example.domain.Task

class TaskListViewModel : ViewModel() {

    private val _rep = TaskRepository.get()

    val tasksLiveData = _rep.GetAll()

    fun CreateTask(task: Task) {
        _rep.Create(task)
    }

    fun UpdateTask(task : Task) {
        _rep.Update(task)
    }

}