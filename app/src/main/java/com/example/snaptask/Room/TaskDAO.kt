package com.example.snaptask.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.snaptask.Model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Insert
    suspend fun insert(task: Task)//Kotlin Coroutine -> Allows CRUD Applications to be performed in the background
    //Kotlin Coroutine -> Allows imp operations to be performed Asynchronously in background
    // ( Prevents App to Run Slowly) -> By Allowing these activities to be performed outside the main thread
    //The Main Thread -> Android Runs the Application in a single thread, the main thread
    //(main thread)-> All components Such as activity & Providers uses the main thread -> Hence we Use Suspend
    //Room Uses Dao to create a clean API by default

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun  delete(task: Task)

    @Query("DELETE FROM TASK_TABLE")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM task_Table ORDER BY id ASC")
    //SELECT * FROM note_table --> Select all the elements from the task table
    //ORDER BY id ASC -->Orders it in based on their id
    fun getALlTask(): Flow<List<Task>>

}