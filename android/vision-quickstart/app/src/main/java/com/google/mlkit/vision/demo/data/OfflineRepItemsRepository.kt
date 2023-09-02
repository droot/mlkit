package com.google.mlkit.vision.demo.data

import kotlinx.coroutines.flow.Flow

class OfflineRepItemsRepository(private val itemDao: RepItemDao) : RepItemsRepository {

    override fun getAllItemsStream(): Flow<List<RepItem>> = itemDao.getAllItems()


    override suspend fun deleteItem(item: RepItem) = itemDao.delete(item)

    override suspend fun updateItem(item: RepItem) = itemDao.update(item)

    override suspend fun upsertItem(item: RepItem) = itemDao.upsert(item)

    override suspend fun insertItem(item: RepItem) = itemDao.insert(item)

    override fun getItemStream(id: Int): Flow<RepItem> = itemDao.getItem(id)
}