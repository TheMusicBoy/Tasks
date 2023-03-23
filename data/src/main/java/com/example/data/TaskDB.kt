package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.Entities.TaskEntity

@Database (
    entities = [ TaskEntity::class ],
    version = 2
)
@TypeConverters(TypeConverter::class)
abstract class TaskDB : RoomDatabase() {

    abstract fun TaskDao() : TaskDao

}