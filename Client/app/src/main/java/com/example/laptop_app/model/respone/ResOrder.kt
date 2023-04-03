package com.example.laptop_app.model.respone

import com.example.laptop_app.model.Cart
import com.example.laptop_app.model.Product

data class ResOrder(
    private var id: String,
    private var idUser: String,
    private var dateTime: String,
    private var shippingStatus: Int,
    private var paymentStatus: Int,
    private var product: Product,
    private var cart: Cart
) {

    fun getId(): String = this.id

    fun getIdUser(): String = this.idUser

    fun getDateTime(): String = this.dateTime

    fun getShippingStatus(): Int = this.shippingStatus

    fun getPaymentStatus(): Int = this.paymentStatus

    fun getProduct(): Product = this.product

    fun getCart(): Cart = this.cart
}