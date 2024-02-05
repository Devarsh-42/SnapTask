package com.example.snaptask.View

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.snaptask.R
import java.util.Calendar

class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var spinner: Spinner
    private lateinit var priority: TextView
    private lateinit var Priority: String
    private lateinit var time: Button
    lateinit var taskText: EditText
    lateinit var save: Button
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var Time: String

    var task_text:String? = null
    var parority_text:String? = null
    var time_Text:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskText = findViewById(R.id.UpdateTaskTV)
        spinner = findViewById(R.id.spinner)
        priority = findViewById(R.id.PriorityInputUpdate)
        save = findViewById(R.id.UpdateBTN)
        time = findViewById(R.id.UpdateTimeBTN)

        initTimepicker()
        spinner.onItemSelectedListener = this

        time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    // Handle the selected time here
                    val selectedTime = "$hourOfDay:$minute"
                    time.text = selectedTime
                    Time = selectedTime
                },
                currentHour,
                currentMinute,
                false
            )
            timePickerDialog.show()
        }

        save.setOnClickListener {
            saveTask()
        }

        val arrayAdapter = ArrayAdapter.createFromResource(
            this, R.array.Priorities, android.R.layout.simple_spinner_item
        )

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }

    fun saveTask() {
        task_text = taskText.text.toString()
        parority_text = priority.text.toString()
        time_Text = time.text.toString()

        val intent = Intent()
        intent.putExtra("taskText",task_text)
        intent.putExtra("Priority",parority_text)
        intent.putExtra("Time",time_Text)
        intent.putExtra("Status","OnGoing")
        setResult(RESULT_OK,intent)
        finish()

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Priority = parent!!.getItemAtPosition(position).toString()
        priority.text = "Priority: $Priority"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Priority = parent!!.getItemAtPosition(position).toString()
        priority.text = "Priority: $Priority"
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Priority = "Low"
        priority.text = "Priority: $Priority"
    }

    private fun initTimepicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Handle the selected time here
                val selectedTime = "$hourOfDay:$minute"
                time.text = selectedTime
                Time = selectedTime
            },
            currentHour,
            currentMinute,
            false
        )
    }
}
