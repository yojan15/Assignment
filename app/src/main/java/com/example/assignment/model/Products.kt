package com.example.assignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Products(
    val image: String,
    val price: Double,
//    val rating: Rating,
    val description: String,
    val id: Long,
    val title: String,
    val category: String
): Parcelable

data class Rating(
    val rate: Double,
    val count: Long
)
