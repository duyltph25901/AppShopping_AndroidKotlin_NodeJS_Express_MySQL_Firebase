package com.example.laptop_app.controller.db_local.system

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.laptop_app.model.MySystem

@Dao
interface SystemDAO {
    @Insert
    fun insert(mySystem: MySystem): Unit

    @Query("select * from System order by `id` desc limit 1")
    fun getSystem(): MySystem

    @Query("delete from System")
    fun clearSystem(): Unit
}