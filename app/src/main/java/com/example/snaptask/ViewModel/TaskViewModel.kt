package com.example.snaptask.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.snaptask.Model.Task
import com.example.snaptask.Repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val myAllTask: LiveData<List<Task>> = repository.myAllTask.asLiveData()

    fun insertTask(taskText: String, priority: String, time: String, status: String) {
        viewModelScope.launch {
            val newTask = Task(task = taskText, Priority = priority, status = status, time = time)
            repository.insert(newTask)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}
