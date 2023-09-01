package com.google.mlkit.vision.demo.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface RepItemsRepository {

    fun getAllItemsStream(): Flow<List<RepItem>>

    fun getItemStream(id: Int): Flow<RepItem>

    suspend fun insertItem(item: RepItem)

    suspend fun updateItem(item: RepItem)

    suspend fun deleteItem(item: RepItem)

}
