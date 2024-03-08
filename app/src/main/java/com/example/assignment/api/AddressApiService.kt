package com.example.assignment.api

import com.example.assignment.model.AddressListResponse
import com.example.assignment.model.AddressRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddressApiService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE3NTkwNjg5LCJpYXQiOjE3MDk4MTQ2ODksImp0aSI6IjQxMTFiZGNkMDU0MjQyMmY5NDE1MmNlMmE1NjJhMmZlIiwidXNlcl9pZCI6NTM0fQ.CUGd1zwR4Alba19aYlPklku4CO85ocflQP1e74Qzk84")
    @POST("app/add_multiple_address_api")
    fun addMultipleAddress(@Body request: AddressRequestBody): Call<AddressListResponse>

}