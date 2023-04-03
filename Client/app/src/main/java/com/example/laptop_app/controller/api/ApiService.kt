package com.example.laptop_app.controller.api

import com.example.laptop_app.model.*
import com.example.laptop_app.model.respone.ResCart
import com.example.laptop_app.model.respone.ResOrder
import com.example.laptop_app.others.Const
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST(Const.handleRegister)
    fun handleRegister(@Body user: User): Call<User>

    @POST(Const.handleLogin)
    fun handleLogin(
        @Body userFound: UserFound
    ): Call<User>

    @GET(Const.getAllUser)
    fun getAllUser(): Call<List<User>>

    @GET(Const.getAllProduct)
    fun getAllProduct(): Call<List<Product>>

    @GET(Const.getAllProductsName)
    fun getAllProductName(): Call<List<String>>

    @GET(Const.getMacbook)
    fun getMacbook(): Call<List<Product>>

    @GET(Const.getUltrabook)
    fun getUltrabook(): Call<List<Product>>

    @GET(Const.getGaming)
    fun getGaming(): Call<List<Product>>

    @GET(Const.getCarts)
    fun getCart(@Query("idUser") idUser: String): Call<List<ResCart>>

    @PUT(Const.updateCart)
    fun updateCart(
        @Path("id") id: String,
        @Body cart: Cart
    ): Call<Int>

    @DELETE(Const.deleteCart)
    fun deleteCart(
        @Path("id") id: String
    ): Call<Int>

    @POST(Const.searchProductByName)
    fun searchProductByName(
        @Body productSearch: ProductSearch
    ): Call<List<Product>>

    @POST(Const.addProductToCart)
    fun addProductToCart(
        @Body addCart: AddCart
    ): Call<Int>

    @POST(Const.updateUser)
    fun updateUser(
        @Body user: User
    ): Call<Int>

    @POST(Const.addOrder)
    fun addOrder(
        @Body order: Order
    ): Call<Int>

    @POST(Const.addCart)
    fun addCart(
        @Body cart: Cart
    ): Call<Int>

    @GET(Const.getOrder)
    fun getOrders(
        @Query("idUser") idUser: String
    ): Call<List<ResOrder>>
}