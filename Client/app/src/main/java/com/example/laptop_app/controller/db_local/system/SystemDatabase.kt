package com.example.laptop_app.controller.db_local.system

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laptop_app.model.MySystem
import com.example.laptop_app.others.Const

@Database(entities = [MySystem::class], version = 1, exportSchema = false)
abstract class SystemDatabase: RoomDatabase() {
    public abstract fun SystemDao(): SystemDAO

    companion object {
        @Volatile
        private var INSTANCE: SystemDatabase? = null

        fun getDatabase(context: Context): SystemDatabase {
            var instanceTemp = INSTANCE
            if (instanceTemp != null) {
                return instanceTemp
            }

            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    SystemDatabase::class.java,
                    Const.dbSystem
                ).allowMainThreadQueries().build()
            }
        }
    }
}