package com.example.assignment.model
data class AddressListResponse(
    val address_list: AddressData
)

data class AddressData(
    val address_id: Int,
    val address: String,
    val google_address: String?,
    val city_id: Int,
    val locality: String?,
    val state_id: Int,
    val prof_zone_id: Int,
    val pincode: String,
    val Address_type: String,
    val caller_id: Int,
    val status: Int,
    val patient_id: String?,
    val last_modified_by: String?
)

data class AddressRequestBody(
    val address: String,
    val city_id: Int,
    val state_id: Int,
    val prof_zone_id: Int,
    val pincode: String,
    val Address_type: String
)
