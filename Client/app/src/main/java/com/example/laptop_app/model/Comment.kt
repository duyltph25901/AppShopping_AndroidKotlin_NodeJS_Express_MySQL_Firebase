package com.example.laptop_app.model

data class Comment(
    private var id: String,
    private var idUser: String,
    private var idProduct: String,
    private var dateTime: String,
    private var content: String
) {
}