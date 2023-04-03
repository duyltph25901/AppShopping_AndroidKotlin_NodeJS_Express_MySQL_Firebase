package com.example.laptop_app.controller.db_local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.laptop_app.model.User

@Dao
interface UserDAO {
    @Insert
    fun insertUser(user: User): Unit

    @Query("select * from User limit 1")
    fun getUserLogged(): User

    @Query("delete from User")
    fun deleteUserCurrent(): Unit
}