package com.example.timetrackerapp.adapter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.entities.TaskEntity

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
            val bundle = Bundle()
            bundle.putInt("Id", taskNamelist[position].id)


            it.findNavController().navigate(R.id.action_home_fragment_to_task_execute,bundle)//


        }

    }


    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.taskName)
        val taskTime : TextView = itemView.findViewById(R.id.taskTime)
        val taskTag : TextView = itemView.findViewById(R.id.taskTag)
        val playBtn : ImageView = itemView.findViewById(R.id.playBtn)


    }
}
