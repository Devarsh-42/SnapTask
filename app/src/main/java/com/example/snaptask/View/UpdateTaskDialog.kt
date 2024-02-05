package com.example.snaptask.View

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.snaptask.R
import com.example.snaptask.Repository.TaskViewModelFactory
import com.example.snaptask.TaskApplication
import com.example.snaptask.ViewModel.TaskViewModel
import java.util.Calendar

class UpdateTaskDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var priority: TextView
    private lateinit var Priority: String
    private lateinit var updateTimeButton: Button
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var Time: String

    var task_text:String? = null
    var parority_text:String? = null
    var time_Text:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel using the factory
        val viewModelFactory =
            TaskViewModelFactory((requireActivity().application as TaskApplication).repository)
        val taskViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(TaskViewModel::class.java)

        // Retrieve data from arguments Bundle
        val taskId = arguments?.getString("taskId")
        val taskText = arguments?.getString("taskText")
        val priority = arguments?.getString("priority")
        val time = arguments?.getString("time")

        // Populate UI elements with data
        val updateTaskEditText = view.findViewById<EditText>(R.id.UpdateTaskTV)
        val priorityTextView = view.findViewById<TextView>(R.id.PriorityInputUpdate)
        val updateTimeButton = view.findViewById<Button>(R.id.UpdateTimeBTN)
        val update =  view.findViewById<Button>(R.id.UpdateBTN)
        val spinner = view.findViewById<Spinner>(R.id.spinner2)
        val cancel = view.findViewById<Button>(R.id.CancelDialogBTN)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        initTimepicker()
        spinner.onItemSelectedListener = this
//        val arrayAdapter = context?.let {
//            ArrayAdapter. createFromResource(
//                it, R.array.Priorities, android.R.layout.simple_spinner_item
//            )
//        }
//        arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = arrayAdapter

        priorityTextView.setText(priority)
        updateTimeButton.setText(time)

        updateTaskEditText.setText(taskText)

        // Set click listener for the Update button
        update.setOnClickListener {
            // Get updated values from UI elements
            task_text = updateTaskEditText.text.toString()
            parority_text = priorityTextView.text.toString().replace("Priority: ", "")
            time_Text = updateTimeButton.text.toString()

            // Update the task in the ViewModel and Database
            val mainActivity : MainActivity = activity as MainActivity
            mainActivity.updateTaskData(task_text!!,parority_text!!,time_Text!!)
            dialog!!.dismiss()
        }
        cancel.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    private fun initTimepicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        timePickerDialog = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Handle the selected time here
                val selectedTime = "$hourOfDay:$minute"
                updateTimeButton.text = selectedTime
                Time = selectedTime
            },
            currentHour,
            currentMinute,
            false

        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Priority = parent!!.getItemAtPosition(position).toString()
        priority.text = "Priority: $Priority"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Priority = parority_text.toString()
        priority.text = "Priority: $Priority"
    }

}