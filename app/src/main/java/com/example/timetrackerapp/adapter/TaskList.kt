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
import com.example.timetrackerapp.database.entities.TaskEntity

class TaskList(val taskNamelist: List<TaskEntity>) : RecyclerView.Adapter<TaskList.MyViewHolder>() {
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
        Log.d("size",position.toString())

    }
    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.taskName)
        val taskTime : TextView = itemView.findViewById(R.id.taskTime)
        val taskTag : TextView = itemView.findViewById(R.id.taskTag)
        val playBtn : ImageButton = itemView.findViewById(R.id.playBtn)


    }
}
