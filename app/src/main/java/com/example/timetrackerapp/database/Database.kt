package com.example.timetrackerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.timetrackerapp.database.convertors.TimeConvertors
import com.example.timetrackerapp.database.dao.TaskDao
import com.example.timetrackerapp.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(TimeConvertors::class)
abstract class Database : RoomDatabase() {


    abstract fun TaskDao():TaskDao

}