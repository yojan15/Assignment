package com.example.assignment.api

import com.example.assignment.model.PostDataResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("pro_app/pro_login/")
    suspend fun postData(@Body request: RequestBody): PostDataResponse
}
