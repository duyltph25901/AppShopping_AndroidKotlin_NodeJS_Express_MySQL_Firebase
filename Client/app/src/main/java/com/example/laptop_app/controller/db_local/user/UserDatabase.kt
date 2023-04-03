package com.example.laptop_app.controller.db_local.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            var instanceTemp = INSTANCE
            if (instanceTemp != null) {
                return instanceTemp
            }

            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    Const.dbLocalUser
                )
                    .allowMainThreadQueries().build()
            }
        }
    }
}