package com.example.timetrackerapp.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.adapter.TaskList
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time

class HomeScreen : AppCompatActivity() {
    lateinit var taskDB: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val taskNameET: = findViewById<>()
//        val tagNameET
        val tasksRecyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        val addbtn: BottomNavigationView = findViewById(R.id.bottom_navigation)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    println(1)

                    true
                }

                R.id.page_2 -> {
                    println(2)


                    // on below line we are creating a new bottom sheet dialog.
                    val dialog = BottomSheetDialog(this)

                    // on below line we are inflating a layout file which we have created.
                    val view = layoutInflater.inflate(R.layout.create_task, null)

                    // on below line we are creating a variable for our button
                    dialog.setContentView(view)
                    dialog.show()
                    // which we are using to dismiss our dialog.

                    true
                }

                R.id.page_3 -> {
                    println(3)
                    true
                }

                else -> {
                    println("ok")
                    false
                }
            }
        }






        taskDB = Room.databaseBuilder(this, Database::class.java, "Tempdb").build()
//println(System.currentTimeMillis())
        GlobalScope.launch {
            taskDB.TaskDao()
                .insertTask(TaskEntity(0, "UI ", "Ok", Time(System.currentTimeMillis())))
        }
        taskDB.TaskDao().getTaskDetails()
            .observe(this, Observer { Log.d("Datashit", it.toString()) })
        // Live data alreadfy works on background thread so no need to add inside global scope






        val a = taskDB.TaskDao().getTaskDetails().observe(this, Observer { it ->
            tasksRecyclerView.adapter = TaskList(it)

        })

        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
    }









    fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragmentContainerView,fragment)
//        transaction.commit()

//        val fragmentManager = supportFragmentManager
//        val fragment = CreateTask()
//        fragment.show((this).supportFragmentManager,"show vgdsd")



//        val fragmentManager = supportFragmentManager
//        val fragment = CreateTask()
//        fragmentManager.beginTransaction()
//            .add(R.id.lol, fragment, "my_popup_fragment_tag")
//            .addToBackStack(null)
//            .commit()


    }

}







