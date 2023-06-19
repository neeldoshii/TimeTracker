package com.example.timetrackerapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [task_execute.newInstance] factory method to
 * create an instance of this fragment.
 */
class task_execute() : Fragment(R.layout.fragment_task_execute) {
    // TODO: Rename and change types of parameters


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

        var idValue : Int?=null

        val id = arguments?.getInt("Id", -1) ?: -1
        if (id != -1) {
            taskNameText.setText(id.toString())
            // Use the position ID as needed

            Log.d("TaskExecuteFragment", "TaskName: $id")
            idValue = id

        }







        taskDB = Room.databaseBuilder(view.context, Database::class.java, "Tempdb").build()


        taskDB.TaskDao().getSpecificData(id)
            .observe(viewLifecycleOwner, Observer {specificData ->
                specificData.forEach { item ->
                    val id = item.id
                    val taskName = item.taskName
                    val taskTag = item.taskTags
                    val taskTime = item.taskTime
//                   println("specificData "+specificData.toString())
                    taskNameText.setText(taskName)
                    taskTagText.setText(taskTag)
                    counterTextView.setText(taskTime.toString())

                    Log.d("id", id.toString())
                    Log.d("taskName", taskName)
                    Log.d("taskTag", taskTag)
                    Log.d("taskTime", taskTime.toString())
                }


            })





























        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment task_execute.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            task_execute().apply {
                arguments = Bundle().apply {

                }
            }
    }
}