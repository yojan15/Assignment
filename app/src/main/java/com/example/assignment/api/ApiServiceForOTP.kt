package com.example.assignment.api

import com.example.assignment.model.PostOtpCheckResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiServiceForOTP {
    @POST("pro_app/pro_otp_chk/")
    suspend fun postOtpCheck(@Body request: RequestBody): PostOtpCheckResponse
}
