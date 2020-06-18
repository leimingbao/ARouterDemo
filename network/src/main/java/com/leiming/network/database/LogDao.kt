package com.leiming.network.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: Logging)

    @Query("select * from Logging")
    fun getAllLog(): MutableList<Logging>
}