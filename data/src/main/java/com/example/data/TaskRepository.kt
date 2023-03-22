package com.example.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.example.domain.ITaskRepository
import com.example.domain.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val DB_NAME = "TaskDatabase"

class TaskRepository private constructor(context: Context)
    : ITaskRepository {

    private val _dao = Room.databaseBuilder(
        context.applicationContext,
        TaskDB::class.java,
        DB_NAME
    )
        .build()
        .TaskDao()

    companion object {
        private var INSTANCE : TaskRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null)
                INSTANCE = TaskRepository(context)
        }

        fun get() : TaskRepository {
            return INSTANCE ?:
                throw IllegalStateException("TaskRepository must be initialized")
        }

    }

    override fun Create(task: Task) {
        GlobalScope.launch {
            _dao.Create(TaskConverter.ToEntity(task))
        }
    }

    override fun Delete(id: Int) {
        GlobalScope.launch {
            _dao.Delete(id)
        }
    }

    override fun Update(task: Task) {
        GlobalScope.launch {
            _dao.Update(TaskConverter.ToEntity(task))
        }
    }

    override fun GetAll(): LiveData<List<Task>> {
        val list = _dao.GetAll()
        return Transformations.map(list) {
            it -> it.map {
                TaskConverter.ToNonNullableModel(it)
            }
        }
    }

    override fun GetById(id : Int): LiveData<Task?> {
        val entity = _dao.GetById(id)
        return Transformations.map(entity) {
            TaskConverter.ToNullableModel(it)
        }
    }
}