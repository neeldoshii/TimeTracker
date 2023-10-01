package com.example.timetrackerapp.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask


class task_execute() : Fragment(R.layout.fragment_task_execute) {
    lateinit var counterTextView: TextView
    private var initialTime: Long = 0
    private var currentTime: Long = 0
    private var isTimerRunning = false
    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private val handler = Handler()
    var resumeFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_task_execute, container, false)
        val taskNameText: TextView = view.findViewById(R.id.taskNameText)
        val taskTagText: TextView = view.findViewById(R.id.taskTagText)
        counterTextView = view.findViewById(R.id.counterTextView)
        val pauseBtn: Button = view.findViewById(R.id.pauseBtn)
        val quitBtn: Button = view.findViewById(R.id.quitBtn)

        val backIcon: MaterialToolbar = view.findViewById(R.id.topAppBar)

        val id = arguments?.getInt("Id", -1) ?: -1
        if (id != -1) {
            taskNameText.setText(id.toString())
            // Use the position ID as needed

            Log.d("TaskExecuteFragment", "TaskName: $id")
            val currentTime: Date = Calendar.getInstance().getTime()
            Log.d("isthis0", currentTime.toString())
        }

        backIcon.setOnClickListener {
            it.findNavController().navigate(R.id.action_task_execute_to_home_fragment)
        }

        quitBtn.setOnClickListener() {

            GlobalScope.launch {
                taskDB.TaskDao()
                    .insertTask(
                        TaskEntity(
                            id,
                            taskNameText.text.toString(),
                            taskNameText.text.toString(),
                            Time.valueOf(counterTextView.text.toString())
                        )
                    )

            }
            Toast.makeText(view.context, "Updated", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_task_execute_to_home_fragment)

        }




        taskDB = Room.databaseBuilder(view.context, Database::class.java, "Tempdb").build()


//        long to time convertors

        fun longToTime(longTime: Long): Time {
            return Time(longTime)
        }


//        //Functions--------------


        fun updateTimer(time1: Time) {


            currentTime = System.currentTimeMillis() - initialTime + time1.time
            Log.d("currentTime Tiem ", longToTime(currentTime).toString())

            counterTextView.setText(longToTime(currentTime).toString())
        }


        fun startTimer(time: Time) {
            Log.d("Timer",time.toString())
            if (!isTimerRunning) {

                val currentTime = System.currentTimeMillis()

//                Log.d("Initial Tiem ", longToTime(time.time).toString())
//                Log.d("1st time system Time ", longToTime(System.currentTimeMillis()).toString())

                initialTime = currentTime

                timer = Timer()
                timerTask = object : TimerTask() {
                    override fun run() {

                        handler.post { updateTimer(time) }
                    }
                }
                timer.scheduleAtFixedRate(timerTask, 0, 1000)
                isTimerRunning = true
            }
        }


        fun resetTimer() {
            timer.cancel()
            isTimerRunning = false
            currentTime = 0
//            updateTimer()
        }


        fun pauseTimer() {
            if (isTimerRunning) {
                timer.cancel()
                isTimerRunning = false
            }


        }















        taskDB.TaskDao().getSpecificData(id)
            .observe(viewLifecycleOwner, Observer { specificData ->
                specificData.forEach { item ->
                    val idval = item.id
                    val taskName = item.taskName
                    val taskTag = item.taskTags
                    val taskTime = item.taskTime
                    taskNameText.setText(taskName)
                    taskTagText.setText(taskTag)
                    counterTextView.setText(taskTime.toString())
//                   println("specificData "+specificData.toString())


                    startTimer(taskTime)
                }

            })


        pauseBtn.setOnClickListener() {
            if (resumeFlag == false) {
                pauseBtn.setText("Resume")
                resumeFlag = true
                pauseTimer()
            } else {
//                Toast.makeText(view.context, "Pause", Toast.LENGTH_SHORT).show()
                pauseBtn.setText("Pause")
                resumeFlag = false

                startTimer(Time.valueOf(counterTextView.text as String?))



            }

        }



//        Stop execute toolbar btn
//        val materialToolbar = view.findViewById<MaterialToolbar>(R.id.materialToolbar)
//        materialToolbar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.stopexecute -> {
//                    GlobalScope.launch {
//                        taskDB.TaskDao()
//                            .insertTask(
//                                TaskEntity(
//                                    id,
//                                    taskNameText.text.toString(),
//                                    taskNameText.text.toString(),
//                                    Time.valueOf(counterTextView.text.toString())
//                                )
//                            )
//
//                    }
//                    Toast.makeText(view.context, "Updated", Toast.LENGTH_SHORT).show()
//                    findNavController().navigate(R.id.action_task_execute_to_home_fragment)
//                    true
//                }
//
//                else -> false
//            }
//        }



        //----------------------------------------

        return view

    }



}

