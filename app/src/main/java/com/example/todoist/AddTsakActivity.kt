package com.example.todoist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var note: EditText
    private lateinit var addTaskBtn: Button

    var itemlist = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tsak)

        title = findViewById(R.id.NewTitleTV)
        note = findViewById(R.id.NewNoteTV)
        addTaskBtn = findViewById(R.id.SaveBTN)

        val intent = intent
        val existingTitleList = intent.getStringArrayListExtra("titleList")
        val existingNoteList = intent.getStringArrayListExtra("noteList")

        if (existingTitleList != null && existingNoteList != null) {
            addTaskBtn.setOnClickListener {
                val titleText = title.text.toString()
                val noteText = note.text.toString()
                existingTitleList.add(titleText)
                existingNoteList.add(noteText)
                val intent = Intent()
                intent.putStringArrayListExtra("titleList", existingTitleList)
                intent.putStringArrayListExtra("noteList", existingNoteList)
                fileHelper.writeData(existingTitleList,existingNoteList,this@AddTaskActivity)
                setResult(RESULT_OK, intent)
                finish()
            }
        } else {
            val titleList = ArrayList<String>()
            val modeList = ArrayList<String>()

            addTaskBtn.setOnClickListener {
                val titleText = title.text.toString()
                val noteText = note.text.toString()
                titleList.add(titleText)
                modeList.add(noteText)

                val intent = Intent()
                intent.putStringArrayListExtra("titleList", titleList)
                intent.putStringArrayListExtra("noteList", modeList)
                setResult(RESULT_OK, intent)
                fileHelper.writeData(titleList,modeList,this@AddTaskActivity)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // Close AddTaskActivity
    }
}
