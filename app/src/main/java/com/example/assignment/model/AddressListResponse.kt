package com.example.assignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class AddressListResponse(
    val address_list: AddressData
)
@Entity(tableName = "address_table")
data class AddressData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
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
    val last_modified_by: String?,
    var isSynced: Boolean = false
)

data class AddressRequestBody(
    val address: String,
    val city_id: Int,
    val state_id: Int,
    val prof_zone_id: Int,
    val pincode: String,
    val Address_type: String
)
