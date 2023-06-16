package com.example.timetrackerapp.homeScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.adapter.TaskList
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import com.example.timetrackerapp.task_execute
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time

class HomeScreen : AppCompatActivity() {
    lateinit var taskDB: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        var homeScreenflag:Boolean=true
//        val tagNameET
        val tasksRecyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)

        taskDB = Room.databaseBuilder(this, Database::class.java, "Tempdb").build()


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    println(1)
                    if(homeScreenflag==false){
                        startActivity(Intent(this, HomeScreen::class.java))
                    }


                    true
                }

                R.id.page_2 -> {
                    println(2)

                     val dialog = BottomSheetDialog(this)


                    val view = layoutInflater.inflate(R.layout.create_task, null)
                    val taskNameET: TextInputEditText = view.findViewById(R.id.taskNameET)
                    val tagNameET: TextInputEditText = view.findViewById(R.id.tagNameET)
                    val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

                    btnClose.setOnClickListener {
                        println(tagNameET.text)
                        println(taskNameET.text)

                        GlobalScope.launch {
                            taskDB.TaskDao()
                                .insertTask(
                                    TaskEntity(
                                        0,
                                        taskNameET.text.toString(),
                                        tagNameET.text.toString(),
                                        Time(System.currentTimeMillis())
                                    )
                                )
                        }
                        dialog.dismiss()
                    }

//                    dialog.setCancelable(false)
                    dialog.setContentView(view)
                    dialog.show()


                    true
                }

                R.id.page_3 -> {
                    homeScreenflag=false
                    println(3)
                    true
                }

                else -> {
                    println("ok")
                    false
                }
            }
        }







        taskDB.TaskDao().getTaskDetails()
            .observe(this, Observer { Log.d("Datashit", it.toString()) })
        // Live data alreadfy works on background thread so no need to add inside global scope


        val a = taskDB.TaskDao().getTaskDetails().observe(this, Observer { it ->
            tasksRecyclerView.adapter = TaskList(it,supportFragmentManager)

        })

        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
    }


}







