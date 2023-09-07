package com.google.mlkit.vision.demo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface RepItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: RepItem)

    @Update
    suspend fun update(item: RepItem)

    @Upsert
    suspend fun upsert(item: RepItem)

    @Delete
    suspend fun delete(item: RepItem)

    @Query("SELECT * from reps WHERE id = :id AND name = :name")
    fun getItem(id: Int, name: String): Flow<RepItem>

    @Query("SELECT * from reps WHERE quantity > 1 ORDER BY lastModifiedTime DESC")
    fun getAllItems(): Flow<List<RepItem>>
}