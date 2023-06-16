package com.example.timetrackerapp.adapter


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.entities.TaskEntity
import com.example.timetrackerapp.task_execute

class TaskList(val taskNamelist: List<TaskEntity>,  val fragmentManager: FragmentManager) : RecyclerView.Adapter<TaskList.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.task_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskNamelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.taskName.text = taskNamelist[position].taskName
        holder.taskTag.text = taskNamelist[position].taskTags
        holder.taskTime.text = taskNamelist[position].taskTime.toString()
        holder.playBtn.setOnClickListener(){
            println("CLick ******" + position)
            val fragmentTransaction = fragmentManager.beginTransaction()

            // creating the instance of the bundle
            val bundle = Bundle()
            bundle.putInt("positionkey", position)
            bundle.putString("TaskName", taskNamelist[position].taskName)

            val taskExecuteFragment = task_execute()
            taskExecuteFragment.arguments = bundle

            fragmentTransaction.add(R.id.space, taskExecuteFragment)
            fragmentTransaction.commit()


        }

    }
    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.taskName)
        val taskTime : TextView = itemView.findViewById(R.id.taskTime)
        val taskTag : TextView = itemView.findViewById(R.id.taskTag)
        val playBtn : ImageButton = itemView.findViewById(R.id.playBtn)


    }
}
