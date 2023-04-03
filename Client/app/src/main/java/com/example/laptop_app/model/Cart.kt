package com.example.laptop_app.model

data class Cart (
    private var id: String,
    private var idUser: String,
    private var idOrder: String? = null,
    private var idProduct: String,
    private var productQuantity: Int,
        ) {

    fun getProductQuantity(): Int = this.productQuantity
}