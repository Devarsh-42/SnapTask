package com.example.todoist

import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var addBtn: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private var titleList = ArrayList<String>()
    private var nodeList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_main)
        addBtn = findViewById(R.id.AddNoteBTN)
        recyclerView = findViewById(R.id.recyclerViewID)

        addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        loadData()

        adapter = NotesAdapter(titleList, nodeList, this@MainActivity) { position ->
            showDeleteConfirmationDialog(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Delete")
        alert.setIcon(R.drawable.baseline_delete_24)
        alert.setMessage("Do You Want to Delete the Task")
        alert.setCancelable(false)
        alert.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        alert.setPositiveButton("Yes") { _, _ ->
            titleList.removeAt(position)
            nodeList.removeAt(position)
            adapter.notifyDataSetChanged()
            saveData()
        }
        alert.create().show()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            val resultData = data ?: return
            titleList = resultData.getStringArrayListExtra("titleList") ?: ArrayList()
            nodeList = resultData.getStringArrayListExtra("noteList") ?: ArrayList()

            if (titleList.isNotEmpty() && nodeList.isNotEmpty()) {
                adapter = NotesAdapter(titleList, nodeList, this@MainActivity) { position ->
                    showDeleteConfirmationDialog(position)
                }
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter
            }
        }
    }

    private fun saveData() {
        fileHelper.writeData(titleList, nodeList, this@MainActivity)
    }

    private fun loadData() {
        val data = fileHelper.readData(this@MainActivity)
        titleList = data.first
        nodeList = data.second
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
    }
}
