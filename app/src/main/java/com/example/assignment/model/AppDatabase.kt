package com.example.assignment.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AddressData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}