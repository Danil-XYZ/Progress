package com.example.progress

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataModel::class], version = 1, exportSchema = false)
abstract class DataDatabase : RoomDatabase() {

    abstract fun dataDao(): MyDataDao

    companion object {
        @Volatile
        private var INSTANCE: DataDatabase? = null

        fun getDatabase(context: Context): DataDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataDatabase::class.java,
                    "data_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }

}