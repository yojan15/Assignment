package com.example.assignment.apiObject

import com.example.assignment.api.AddressApiService
import com.example.assignment.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private const val BASE_URL = "http://103.186.133.168:8008/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: AddressApiService = retrofit.create(AddressApiService::class.java)
}
