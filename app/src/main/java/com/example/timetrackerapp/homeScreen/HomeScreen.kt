package com.example.timetrackerapp.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrackerapp.R
import com.example.timetrackerapp.adapter.TaskList

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclersongs: RecyclerView = findViewById(R.id.tasksRecyclerView)
        val slist = listOf<String>("Neel","OK","Siddh","Neel","OK","Siddh","Neel","OK","Siddh","Neel","OK","Siddh")



        recyclersongs.adapter = TaskList(slist,slist)
        recyclersongs.layoutManager= LinearLayoutManager(this)






    }
}