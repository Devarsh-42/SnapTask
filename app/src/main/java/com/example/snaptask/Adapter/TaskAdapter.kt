package com.example.snaptask.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.snaptask.Model.Task
import com.example.snaptask.R


class TaskAdapter(val itemClickListner: onItemClickListner) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList: List<Task> = ArrayList()

    interface onItemClickListner{
        fun onItemClicked(taskList: List<Task>,position: Int)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.TaskTextView)
        val priority: TextView = itemView.findViewById(R.id.PriorityID)
        val time: TextView = itemView.findViewById(R.id.Deadline_TimeID)
        val status: TextView = itemView.findViewById(R.id.StatusID)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(view)

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {


        when (taskList[position].Priority.toLowerCase()) {
            "low" -> holder.cardView.setBackgroundColor(Color.parseColor("#F80E0E"))
            "medium" -> holder.cardView.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.cardView.setBackgroundColor(Color.parseColor("#FFB800"))
        }
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.status.text = "Done"
                holder.cardView.setBackgroundColor(Color.parseColor("#00E209"))
            } else {
                holder.status.text = "OnGoing"
                // Set the background color based on priority when the checkbox is not checked
                when (taskList[position].Priority.toLowerCase()) {
                    "low" -> holder.cardView.setBackgroundColor(Color.parseColor("#FFB800"))
                    "medium" -> holder.cardView.setBackgroundColor(Color.parseColor("#EDC988"))
                    else -> holder.cardView.setBackgroundColor(Color.parseColor("#F80E0E"))
                }
            }
        }

        val currTask : Task = taskList[position]
        holder.taskText.text = currTask.task
        holder.priority.text = currTask.Priority
        holder.time.text = currTask.time

        // Add any additional click listeners or actions here
        holder.cardView.setOnClickListener {
            itemClickListner.onItemClicked(taskList,position)
        }
    }

    fun setTask(myTask: List<Task>) {
        this.taskList = myTask
        notifyDataSetChanged()
    }

    fun getTask(position: Int): Task {
        return taskList[position]
    }

    // Method to update the list of tasks
    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}
