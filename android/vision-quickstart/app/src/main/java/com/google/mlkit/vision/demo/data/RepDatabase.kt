package com.google.mlkit.vision.demo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RepItem::class], version = 1, exportSchema = false)
abstract class RepDatabase: RoomDatabase() {

    abstract fun repItemDao(): RepItemDao

    companion object{

        @Volatile
        private var Instance: RepDatabase? = null

        fun getDatabase(context: Context): RepDatabase {
            return Instance ?: synchronized(this) {

                Room.databaseBuilder(context, RepDatabase::class.java, "reps-db").
                fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}