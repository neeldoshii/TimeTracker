package com.example.timetrackerapp.Fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.Database
import java.sql.Time
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
            Log.d("isthis0",currentTime.toString())
        }







        taskDB = Room.databaseBuilder(view.context, Database::class.java, "Tempdb").build()



//        long to time convertors



        fun longToTime(longTime : Long) : Time{
            return  Time(longTime)
        }





//        //Functions--------------
//

        fun updateTimer(time1:Time) {


            currentTime = System.currentTimeMillis() - initialTime + time1.time
            Log.d("currentTime Tiem ",longToTime(currentTime).toString())

//            val seconds = (currentTime / 1000) % 60
//            val minutes = (currentTime / (1000 * 60)) % 60
//            val hours = (currentTime / (1000 * 60 * 60)) % 24
//            val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
//            timerTextView.text = timeString

            counterTextView.setText(longToTime(currentTime).toString())
        }


        fun startTimer(time: Time) {
            if (!isTimerRunning) {

                val currentTime =System.currentTimeMillis()
                val elapsedTime = currentTime - time.time
//                val elapsedTime = currentTime + time.time

                Log.d("Initial Tiem ",longToTime(time.time).toString())
                Log.d("1st time system Time ",longToTime(System.currentTimeMillis()).toString())



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

//
////        fun startTimer(time: String) {
////            if (!isTimerRunning) {
////                val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
////                val currentDate = Date()
////                val currentTime = dateFormat.format(currentDate)
////                val initialDateTime = dateFormat.parse(currentTime.substring(0, 8))
////                val storedTime = dateFormat.parse(time)
////
////                val elapsedTime = storedTime.time + initialDateTime.time
////
////                initialTime = elapsedTime
////                timer = Timer()
////                timerTask = object : TimerTask() {
////                    override fun run() {
////                        handler.post { updateTimer() }
////                    }
////                }
////                timer.scheduleAtFixedRate(timerTask, 0, 1000)
////                isTimerRunning = true
////            }
////        }
//
//
        fun pauseTimer() {
            if (isTimerRunning) {
                timer.cancel()
                isTimerRunning = false
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


//                    Log.d("id", idval.toString())
//                    Log.d("taskName", taskName)
//                    Log.d("taskTag", taskTag)
//                    Log.d("taskTime", taskTime.toString())
//
//                    Log.d("shgdsdsdsd", taskName.toString())
//                    println("Time to long hai"+longToTime(taskTime.time))
//
//                    Log.d("okie",Calendar.getInstance().timeInMillis.toString())
//                    println("******************"+taskTime.time)
                    startTimer(taskTime)
                }

            })


        //----------------------------------------

        return view

    }


}

