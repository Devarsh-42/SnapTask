package com.example.snaptask.View

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snaptask.Adapter.TaskAdapter
import com.example.snaptask.Model.Task
import com.example.snaptask.R
import com.example.snaptask.Repository.TaskViewModelFactory
import com.example.snaptask.TaskApplication
import com.example.snaptask.ViewModel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), TaskAdapter.onItemClickListner {

    private lateinit var addBTN: ImageView
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBTN = findViewById(R.id.AddTaskBTN)
        addBTN.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        val viewModelFactory = TaskViewModelFactory((application as TaskApplication).repository)

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProvider(this@MainActivity, viewModelFactory)
            .get(TaskViewModel::class.java)

        taskAdapter = TaskAdapter(this)

        // Observe the LiveData from the ViewModel and update the UI when the data changes
        taskViewModel.myAllTask.observe(this@MainActivity,
            Observer { task ->
                taskAdapter.setTask(task)
            })
        recyclerView.adapter = taskAdapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                taskViewModel.deleteTask(taskAdapter.getTask(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Task Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val taskText = it.getStringExtra("taskText") ?: ""
                val priority = it.getStringExtra("Priority") ?: ""
                val time = it.getStringExtra("Time") ?: ""
                val status = it.getStringExtra("Status")

                // Add the new task to the database using the ViewModel
                CoroutineScope(Dispatchers.IO).launch {
                    taskViewModel.insertTask(taskText, priority, time, status!!)
                }
            }
        }
    }

    override fun onItemClicked(taskList: List<Task>, position: Int) {
        val selectedTask = taskList[position]

        // Create a Bundle to pass data to the UpdateFragment
        val bundle = Bundle()
        bundle.putString("taskId", selectedTask.id.toString())
        bundle.putString("taskText", selectedTask.task)
        bundle.putString("priority", selectedTask.Priority)
        bundle.putString("time", selectedTask.time)

        // Create an instance of UpdateTaskDialog and set arguments
        val updateTaskDialog = UpdateTaskDialog()
        updateTaskDialog.arguments = bundle

        // Set the taskId for use in the UpdateTaskDialog
        val fragmentManager: FragmentManager = supportFragmentManager
        updateTaskDialog.show(fragmentManager, "updateFragment")
    }

    // Function to handle updated data from the UpdateTaskDialog
    fun updateTaskData(taskText: String, priority: String, time: String) {
        val updatedTask = Task(taskText, priority, time, "Ongoing")
        updatedTask.id = taskId
        taskViewModel.updateTask(updatedTask)
    }
}
