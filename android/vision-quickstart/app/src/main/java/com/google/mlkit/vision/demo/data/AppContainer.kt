package com.google.mlkit.vision.demo.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: RepItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */

    override val itemsRepository: RepItemsRepository by lazy {
        OfflineRepItemsRepository(RepDatabase.getDatabase(context).repItemDao())
    }
}