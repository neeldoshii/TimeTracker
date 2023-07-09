package com.example.timetrackerapp.fragment




import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.timetrackerapp.R
import com.example.timetrackerapp.adapter.SwipeToDeleteCallback
import com.example.timetrackerapp.adapter.TaskList
import com.example.timetrackerapp.api.QuotesData
import com.example.timetrackerapp.api.quoteObject
import com.example.timetrackerapp.database.Database
import com.example.timetrackerapp.database.entities.TaskEntity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.sql.Time


lateinit var taskDB: Database

class Home_fragment : Fragment(R.layout.fragment_home_fragment) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        var homeScreenflag: Boolean = true
        val tasksRecyclerView: RecyclerView = view.findViewById(R.id.tasksRecyclerView)



        taskDB = Room.databaseBuilder(view.context, Database::class.java, "Tempdb").build()


        //Switch case method for bottom navigation

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    println(1)

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
                    homeScreenflag = false
                    println(3)

                    true
                }

                else -> {
                    println("ok")
                    false
                }
            }
        }


        //Swipe Method

            val a = taskDB.TaskDao().getTaskDetails().observe(viewLifecycleOwner, Observer { it ->
            tasksRecyclerView.adapter = TaskList(it, childFragmentManager)

        })

        tasksRecyclerView.layoutManager = LinearLayoutManager(view.context)

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            taskList access the TaskList adpater class
                val taskList = (tasksRecyclerView.adapter as TaskList)

                val position = viewHolder.adapterPosition
                val task_id_item=taskList.taskNamelist[position].id   //here taskNameList access the list fields

                GlobalScope.launch {
                    taskDB.TaskDao().deleteSpecificData(task_id_item)

                }
                tasksRecyclerView.adapter?.notifyItemRemoved(position)
            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            com.example.timetrackerapp.R.color.white
                        )
                    )
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c!!, recyclerView!!,
                    viewHolder!!, dX, dY, actionState, isCurrentlyActive
                )
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(view.context)

        val quotesText : TextView = view.findViewById(R.id.quotes)

        val quoteObj= quoteObject.news.getHeadlines()
  quoteObj.enqueue(object :retrofit2.Callback<QuotesData> {
      override fun onResponse(call: Call<QuotesData>, response: Response<QuotesData>) {

          quotesText.setText(response.body()?.content.toString() + "\n\n~"+response.body()?.author.toString())
          Log.d("quorte", response.body()?.content.toString())
      }

      override fun onFailure(call: Call<QuotesData>, t: Throwable) {
          quotesText.setText("Never, never, never give up."  + "\n\n~" + "Issac Newton")

      }

  })




        // Inflate the layout for this fragment
        return view
    }


}