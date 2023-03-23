package com.example.danilaproject.DetailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.data.TaskRepository
import com.example.domain.Task
import java.util.*

class DetailsViewModel : ViewModel() {

    private val _rep = TaskRepository.get()
    private val _taskIdLiveData = MutableLiveData<UUID>()

    var taskLiveData : LiveData<Task?> =
        Transformations.switchMap(_taskIdLiveData) {
            _rep.GetById(it)
        }

    fun LoadTask(taskId : UUID) {
        _taskIdLiveData.value = taskId
    }

    fun UpdateTask(task : Task) {
        _rep.Update(task)
    }

    fun DeleteTask(taskId : UUID) {
        _rep.Delete(taskId)
    }

}