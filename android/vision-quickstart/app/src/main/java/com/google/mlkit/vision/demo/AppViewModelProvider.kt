package com.google.mlkit.vision.demo


import android.app.Application
import androidx.compose.material3.rememberDatePickerState
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.mlkit.vision.demo.data.AppDataContainer
import com.google.mlkit.vision.demo.data.RepItemsRepository

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            val repository: RepItemsRepository = (this[APPLICATION_KEY] as RepsTrackerApplication).container.itemsRepository
            RepsTrackerViewModel(repository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
//fun CreationExtras.repsTrackerApplication(): RepsTrackerApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as RepsTrackerApplication)
