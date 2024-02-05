package com.example.snaptask.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.snaptask.Model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create an instance of the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java, "task_database"
                )
                    .addCallback(TaskDatabaseCallback(applicationScope))
                    .build()

                // Set the instance variable to the newly created database
                INSTANCE = instance
                instance
            }
        }
    }

    // Callback to populate the database with initial data
    private class TaskDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                // Inside a coroutine scope to perform asynchronous operations
                scope.launch {
                    // Obtain the TaskDAO instance from the database
                    val taskDao = database.getTaskDao()

                    // Insert initial tasks into the database
                    taskDao.insert(Task("Test Title 1", "Low", "Ongoing", "10:00"))
                    taskDao.insert(Task("Test Title 2", "High", "Ongoing", "12:00"))
                    // Add more initial tasks if needed
                }
            }
        }
    }
}
