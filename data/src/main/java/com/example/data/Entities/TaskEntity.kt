package com.example.data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Tasks"
)
data class TaskEntity (
    @PrimaryKey
    val Id : Int,
    @ColumnInfo(name = "title")
    var Title : String,
    @ColumnInfo(name = "description")
    var Description : String,
    @ColumnInfo(name = "mark")
    var Mark : Boolean,
    @ColumnInfo(name = "completed")
    var Completed : Boolean
)
