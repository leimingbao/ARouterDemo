package com.leiming.network.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Logging::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getLoggingDao(): LogDao
}