package com.example.timetrackerapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.timetrackerapp.database.entities.TaskEntity

@Dao
interface TaskDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(xyz:TaskEntity) // xyz is a object and Employee is data class

    @Delete
    suspend fun deletedata(xyz:TaskEntity) // xyz is a object and Employee is data class

    @Update
    suspend fun updatedata(xyz:TaskEntity) // xyz is a object and Employee is data class

    @Query("Select * from taskTable")
    fun getTaskDetails() : LiveData<List<TaskEntity>>

//    @Query("SELECT * FROM taskTable WHERE id=:taskId")
//    fun getSpecificData(taskId: Int): LiveData<List<TaskEntity>>

    @Query("SELECT id,taskName, taskTags,taskTime FROM taskTable WHERE id=:taskId")
    fun getSpecificData(taskId: Int): LiveData<List<TaskEntity>>


    @Query("Delete FROM taskTable WHERE id=:id")
    fun deleteSpecificData(id:Int): Int


}
