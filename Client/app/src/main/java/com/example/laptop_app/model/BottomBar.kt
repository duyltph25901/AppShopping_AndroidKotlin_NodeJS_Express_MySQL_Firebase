package com.example.laptop_app.model

class BottomBar (
    private var id: Int,
    private var image: Int,
    private var name: String
        ) {

    fun getId(): Int = this.id

    fun getImage(): Int = this.image
}