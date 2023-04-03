package com.example.laptop_app.model

data class ProductSearch (
    private var name: String
        ) {

    private fun getName(): String = this.name

    private fun setName(name: String): Unit {
        this.name = name
    }
}