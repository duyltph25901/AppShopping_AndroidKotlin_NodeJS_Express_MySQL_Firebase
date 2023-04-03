package com.example.laptop_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "System")
data class MySystem (
    private var isLogged: Boolean  = false
        ) {

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    public fun setId(id: Long): Unit {
        this.id = id
    }

    public fun getId(): Long = this.id

    public fun setIsLogged(isLogged: Boolean): Unit {
        this.isLogged = isLogged
    }

    public fun isLogged(): Boolean = this.isLogged
}