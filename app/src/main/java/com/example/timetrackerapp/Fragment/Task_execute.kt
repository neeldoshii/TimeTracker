package com.example.timetrackerapp.Fragment

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
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes


class task_execute() : Fragment(R.layout.fragment_task_execute) {

    private var initialTime: Long = 0
    private var currentTime: Long = 0
    private var isTimerRunning = false
    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private val handler = Handler()
    var a: Long = 0
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
        val counterTextView: TextView = view.findViewById(R.id.counterTextView)
        val pauseBtn: Button = view.findViewById(R.id.pauseBtn)
        val quitBtn: Button = view.findViewById(R.id.quitBtn)


        val id = arguments?.getInt("Id", -1) ?: -1
        if (id != -1) {
            taskNameText.setText(id.toString())
            // Use the position ID as needed

            Log.d("TaskExecuteFragment", "TaskName: $id")
            val currentTime: Date = Calendar.getInstance().getTime()
            Log.d("isthis0", currentTime.toString())
        }

        pauseBtn.setOnClickListener() {
            pauseBtn.setText("Resume")
            pauseTimer()
        }

        quitBtn.setOnClickListener(){

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
//

        fun updateTimer(time1: Time) {


            currentTime = System.currentTimeMillis() - initialTime + time1.time
            Log.d("currentTime Tiem ", longToTime(currentTime).toString())

            counterTextView.setText(longToTime(currentTime).toString())
        }


        fun startTimer(time: Time) {
            if (!isTimerRunning) {

                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - time.time
//                val elapsedTime = currentTime + time.time

                Log.d("Initial Tiem ", longToTime(time.time).toString())
                Log.d("1st time system Time ", longToTime(System.currentTimeMillis()).toString())



                initialTime = currentTime
//                println("time.toString().toLong()"+time.time)
                timer = Timer()
                timerTask = object : TimerTask() {
                    override fun run() {

//                        Log.d("a",a.toString())
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


        //----------------------------------------

        return view

    }

    fun pauseTimer() {
        if (isTimerRunning) {
            timer.cancel()
            isTimerRunning = false
        }
    }
}

