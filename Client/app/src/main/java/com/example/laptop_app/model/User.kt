package com.example.laptop_app.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User constructor(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private var id: String,
    private var email: String,
    private var userName: String,
    private var password: String,
    private var phoneNumber: String,
    private var address: String,
    private var image: String
) {

    fun getId(): String = this.id

    fun getPassword(): String = this.password

    fun getEmail(): String = this.email

    fun getUserName(): String = this.userName

    fun getPhoneNumber(): String = this.phoneNumber

    fun getImage(): String = this.image

    fun getAddress(): String = this.address

    fun setIdUser(id: String): Unit {
        this.id = id
    }

    fun setImage(image: String): Unit {
        this.image = image
    }

    fun setPass(password: String): Unit {
        this.password = password
    }

    fun setEmail(email: String): Unit {
        this.email = email
    }

    fun setPhoneNumber(phoneNumber: String): Unit {
        this.phoneNumber = phoneNumber
    }

    fun setUserName(userName: String): Unit {
        this.userName = userName
    }

    fun setAddress(address: String): Unit {
        this.address = address
    }
}