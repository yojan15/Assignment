package com.example.assignment.model

data class AllAddress (
    val address_list: List<AddressList>?
)
data class AddressList (
    val address_id: Long,
    val address: String,
    val pincode: String,
    val locality: String?,
    val google_address: String?,
    val zone: String,
    val state: String,
    val city: String
)
