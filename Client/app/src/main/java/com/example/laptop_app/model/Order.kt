package com.example.laptop_app.model

data class Order(
   private var id: String,
   private var idUser: String,
   private var dateTime: String,
   private var shippingStatus: Int,
   private var paymentStatus: Int
) {

   fun getId(): String = this.id
}