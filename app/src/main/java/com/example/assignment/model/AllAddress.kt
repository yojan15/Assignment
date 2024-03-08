package com.example.assignment.model

data class AllAddress (
    val addressList: List<AddressList>
)

data class AddressList (
    val pincode: String,
    val address: String,
    val zone: Zone,
    val city: City,
    val addressID: Long,
    val state: State
)
enum class City {
    Pune
}

enum class State {
    Maharashtra
}

enum class Zone {
    Kothrud
}
