package com.example.laptop_app.model

data class UserFound (
    private val email: String,
    private val password: String
        ) {

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }
}