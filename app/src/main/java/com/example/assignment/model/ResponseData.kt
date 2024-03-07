
package com.example.assignment.model

data class ResData(
    val phone_no: Long,
    val OTP: String,
    val msg: String
)

data class TokenStatus(
    val clg_id: Int,
    val token: String
)

data class PostDataResponse(
    val Res_Data: ResData,
    val token_status: TokenStatus
)
