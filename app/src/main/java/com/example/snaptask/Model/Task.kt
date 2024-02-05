package com.example.snaptask.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_Table")
class Task(val task: String, val Priority: String, val status: String, val time: String) {

    @PrimaryKey(autoGenerate = true) //DB Automatically Assign The ID
    var id = 0//Primary Key -> Only one of this value in the Database

}