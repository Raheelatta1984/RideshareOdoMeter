package com.rideshare.odometer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rideshare.odometer.data.TripEntity
import com.rideshare.odometer.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<List<TripEntity>>(emptyList())
    val trips: StateFlow<List<TripEntity>> = _trips.asStateFlow()

    private val _currentTrip = MutableStateFlow<TripEntity?>(null)
    val currentTrip: StateFlow<TripEntity?> = _currentTrip.asStateFlow()

    fun loadAllTrips() {
        viewModelScope.launch {
            repository.getAllTrips().collect { _trips.value = it }
        }
    }

    fun startNewTrip(startKm: Double, purpose: String = TripEntity.PURPOSE_BUSINESS, location: String? = null) {
        val newTrip = TripEntity(
            date = LocalDate.now(),
            timeStart = LocalTime.now(),
            startKm = startKm,
            purpose = purpose,
            location = location
        )
        _currentTrip.value = newTrip
    }

    fun endCurrentTrip(endKm: Double, endLocation: String? = null, notes: String? = null) {
        val current = _currentTrip.value ?: return

        val distance = if (endKm > current.startKm) endKm - current.startKm else 0.0

        val completedTrip = current.copy(
            timeEnd = LocalTime.now(),
            endKm = endKm,
            endLocation = endLocation,
            notes = notes,
            distanceKm = distance,
            updatedAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.insertTrip(completedTrip)
            _currentTrip.value = null
            loadAllTrips()
        }
    }

    fun cancelCurrentTrip() {
        _currentTrip.value = null
    }
}
