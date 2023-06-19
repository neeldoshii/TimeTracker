package com.example.timetrackerapp.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "taskTable")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskName: String,
    val taskTags: String,
    val taskTime: Time
)