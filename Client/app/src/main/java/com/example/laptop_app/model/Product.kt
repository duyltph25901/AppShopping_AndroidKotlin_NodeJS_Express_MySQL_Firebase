package com.example.laptop_app.model

import java.io.Serializable

data class Product(
    private var id: String,
    private var name: String,
    private var price: Double,
    private var description: String,
    private var discount: Int,
    private var image: String,
    private var quantity: Int,
    private var purchases: Int,
    private var linkVideoReview: String,
    private var category: String
) : Serializable {

    fun getId(): String = this.id
    fun getName(): String = this.name
    fun getPrice(): Double = this.price
    fun getDescription(): String = this.description
    fun getDiscount(): Int = this.discount
    fun getImage(): String = this.image
    fun getQuantity(): Int = this.quantity
    fun getPurchases(): Int = this.purchases
    fun getLinkVideo(): String = this.linkVideoReview
    fun getCategory(): String = this.category

    fun getTax(): Double = (
            tax(this.price - (this.price * discount / 100)).toDouble()
            )

    private fun tax(input: Double): String =
        if (getDiscount() == 0) {
            "$input"
        } else {
            String.format("%.2f", input)
        }
}