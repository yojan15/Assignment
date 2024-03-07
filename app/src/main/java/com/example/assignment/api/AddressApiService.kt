package com.example.assignment.api

import com.example.assignment.model.AddressListResponse
import com.example.assignment.model.AddressRequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddressApiService {
    @POST("app/add_multiple_address_api")
    suspend fun addMultipleAddress(
        @Header("Authorization") token: String,
        @Body request: AddressRequestBody
    ): AddressListResponse
}
