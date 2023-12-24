package com.example.todoist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {
        private val FILENAME = "DataInfo.dat"

        fun writeData(item1: ArrayList<String>, item2: ArrayList<String>, context: Context) {
                val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
                val oas = ObjectOutputStream(fos)
                oas.writeObject(item1)
                oas.writeObject(item2)
                oas.close()
        }

        fun readData(context: Context): Pair<ArrayList<String>, ArrayList<String>> {
                var itemList1 = ArrayList<String>()
                var itemList2 = ArrayList<String>()

                try {
                        val fis: FileInputStream = context.openFileInput(FILENAME)
                        val ois = ObjectInputStream(fis)
                        itemList1 = ois.readObject() as ArrayList<String>
                        itemList2 = ois.readObject() as ArrayList<String>
                        ois.close()
                } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                }

                return Pair(itemList1, itemList2)
        }
}
