package com.example.laptop_app.model

data class Category (
    private val id: Int,
    private val name: String
        ) {

    fun getName(): String = this.name

    fun getId(): Int = this.id
}