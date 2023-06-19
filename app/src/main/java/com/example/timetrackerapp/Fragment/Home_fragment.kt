package com.example.timetrackerapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time

lateinit var taskDB: Database
class Home_fragment : Fragment(R.layout.fragment_home_fragment) {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        var homeScreenflag:Boolean=true
        val tasksRecyclerView: RecyclerView = view.findViewById(R.id.tasksRecyclerView)


        taskDB = Room.databaseBuilder(view.context, Database::class.java, "Tempdb").build()


        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    println(1)
//                    if(homeScreenflag==false){
//                        startActivity(Intent(this, HomeScreen::class.java))
//                    }


                    true
                }

                R.id.page_2 -> {
                    println(2)
                    val dialog = BottomSheetDialog(view.context)


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
                                        Time.valueOf("00:00:00")
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
//                    view.findNavController().navigate(R.id.action_home_fragment_to_task_execute2)
                    true
                }

                else -> {
                    println("ok")
                    false
                }
            }
        }

//
//        taskDB.TaskDao().getTaskDetails()
//            .observe(viewLifecycleOwner, Observer { Log.d("Datashit", it.toString()) })


        // Live data alreadfy works on background thread so no need to add inside global scope


        val a = taskDB.TaskDao().getTaskDetails().observe(viewLifecycleOwner, Observer { it ->
            tasksRecyclerView.adapter = TaskList(it, childFragmentManager)

        })

        tasksRecyclerView.layoutManager = LinearLayoutManager(view.context)




        // Inflate the layout for this fragment
        return view
    }


}