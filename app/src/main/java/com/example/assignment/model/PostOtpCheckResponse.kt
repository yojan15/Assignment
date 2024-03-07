package com.example.assignment.model

data class PostOtpCheckResponse (
    val message: String,
    val token: Token
)

data class Token (
    val access: String,
    val refresh: String,
    val colleague: Colleague
)

data class Colleague (
    val srvProfID: Long,
    val profDocVerified: Boolean,
    val callerID: Long,
    val profInterviewed: Boolean,
    val profRegistered: Boolean,
    val callerRegistred: Boolean,
    val clgRefID: String
)
