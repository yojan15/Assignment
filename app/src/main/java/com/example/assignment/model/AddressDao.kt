package com.example.assignment.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressData: AddressData)

    @Query("SELECT * FROM address_table")
    suspend fun getAllAddresses(): List<AddressData>


    @Query("SELECT * FROM address_table WHERE isSynced = 0")
    suspend fun getUnsyncedAddresses(): List<AddressData>
}