package com.example.laptop_app.model.respone

import com.example.laptop_app.model.Product

data class ResCart(
    private var id: String,
    private var idUser: String,
    private var idOrder: String,
    private var product: Product,
    private var productQuantity: Int,
) {
    fun getId(): String = this.id

    fun getIdUser(): String = this.idUser

    fun getIdOrder(): String = this.idOrder

    fun getProduct(): Product = this.product

    fun getProductQuantity(): Int = this.productQuantity

    fun setProductQuantity(productQuantity: Int): Unit {
        this.productQuantity = productQuantity
    }

    fun setIdOrder(idOrder: String): Unit {
        this.idOrder = idOrder
    }
}