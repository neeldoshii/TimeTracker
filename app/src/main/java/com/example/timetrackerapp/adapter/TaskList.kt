package com.example.timetrackerapp.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.timetrackerapp.R

class TaskList(var taskNamelist: List<String>, var taskTagList: List<String>) : RecyclerView.Adapter<TaskList.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.task_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskNamelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.taskName.text = taskNamelist[position]
        holder.taskTag.text = taskTagList[position]
        Log.d("size",position.toString())

    }
    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.taskName)
        val taskTime : TextView = itemView.findViewById(R.id.taskTime)
        val taskTag : TextView = itemView.findViewById(R.id.taskTag)
        val playBtn : ImageButton = itemView.findViewById(R.id.playBtn)


    }
}
