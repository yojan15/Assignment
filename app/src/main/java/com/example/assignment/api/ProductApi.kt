package com.example.assignment.api

import com.example.assignment.model.Products
import retrofit2.Call
import retrofit2.http.GET
interface ProductApi {
    @GET("products")
    fun getProducts() :Call<List<Products>>
}