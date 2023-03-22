package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.Entities.TaskEntity

@Database (
    entities = [ TaskEntity::class ],
    version = 1
)
abstract class TaskDB : RoomDatabase() {

    abstract fun TaskDao() : TaskDao

}