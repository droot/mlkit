package com.google.mlkit.vision.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.mlkit.vision.demo.data.RepItem
import com.google.mlkit.vision.demo.data.RepItemDao
import com.google.mlkit.vision.demo.data.RepItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RepsTrackerViewModel(private val itemsRepository: RepItemsRepository): ViewModel() {
    // Get full bus schedule from Room DB
//    fun getFullSchedule(): Flow<List<BusSchedule>> = busScheduleDao.getAll()
    // Get bus schedule based on the stop name from Room DB
//    fun getScheduleFor(stopName: String): Flow<List<BusSchedule>> =
//        busScheduleDao.getByStopName(stopName)

    val homeUiState: StateFlow<HomeUiState> = itemsRepository.getAllItemsStream().map {HomeUiState(it)}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
    initialValue = HomeUiState()
    )

//    suspend fun upsertItem(item: RepItem) = itemsRepository.upsertItem(item)
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<RepItem> = listOf())