package com.google.mlkit.vision.demo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RepItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: RepItem)

    @Update
    suspend fun update(item: RepItem)

    @Delete
    suspend fun delete(item: RepItem)

    @Query("SELECT * from reps WHERE id = :id")
    fun getItem(id: Int): Flow<RepItem>

    @Query("SELECT * from reps")
    fun getAllItems(): Flow<List<RepItem>>
}