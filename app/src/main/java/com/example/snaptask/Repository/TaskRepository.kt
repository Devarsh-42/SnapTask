package com.example.snaptask.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snaptask.Model.Task
import com.example.snaptask.Room.TaskDAO
import com.example.snaptask.ViewModel.TaskViewModel
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDAO: TaskDAO) {

    val myAllTask: Flow<List<Task>> = taskDAO.getALlTask()

    @WorkerThread
    suspend fun insert(task: Task) {
        taskDAO.insert(task)
    }

    @WorkerThread
    suspend fun update(task: Task) {
        taskDAO.update(task)
    }

    @WorkerThread
    suspend fun delete(task: Task) {
        taskDAO.delete(task)
    }

    @WorkerThread
    suspend fun deleteAllTasks() {
        taskDAO.deleteAllNotes()
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

