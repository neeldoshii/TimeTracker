package com.example.timetrackerapp.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.adapter.TaskList
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time

class HomeScreen : AppCompatActivity() {
    lateinit var taskDB:Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclersongs: RecyclerView = findViewById(R.id.tasksRecyclerView)
        val slist = listOf<String>("Neel","OK","Siddh","Neel","OK","Siddh","Neel","OK","Siddh","Neel","OK","Siddh")


        taskDB = Room.databaseBuilder(this,Database::class.java,"Tempdb").build()
//println(System.currentTimeMillis())
        GlobalScope.launch {
//            taskDB.TaskDao().insertTask(TaskEntity(0,"UI ","Ok",Time(System.currentTimeMillis())))
        }
        taskDB.TaskDao().getTaskDetails().observe(this, Observer { Log.d("Datashit",it.toString()) })    // Live data alreadfy works on background thread so no need to add inside global scope
        recyclersongs.adapter = TaskList(slist,slist)
        recyclersongs.layoutManager= LinearLayoutManager(this)
    }







    }
