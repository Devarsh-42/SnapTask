package com.example.snaptask

import android.app.Application
import com.example.snaptask.Repository.TaskRepository
import com.example.snaptask.Room.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TaskApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())// To add Initial values to the Database

    val database by lazy { TaskDatabase.getDatabase(this@TaskApplication,applicationScope) }
    val repository by lazy { TaskRepository(database.getTaskDao()) }
}