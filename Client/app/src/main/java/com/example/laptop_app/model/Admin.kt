package com.example.laptop_app.model

data class Admin(
    private var id: String,
    private var email: String,
    private var userName: String,
    private var password: String,
    private var phoneNumber: String,
    private var isBoss: Int,
    private var image: String
) {}