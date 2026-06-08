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

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<List<TripEntity>>(emptyList())
    val trips: StateFlow<List<TripEntity>> = _trips.asStateFlow()

    private val _currentTrip = MutableStateFlow<TripEntity?>(null)
    val currentTrip: StateFlow<TripEntity?> = _currentTrip.asStateFlow()

    fun loadTrips() {
        viewModelScope.launch {
            repository.getAllTrips().collect { tripList ->
                _trips.value = tripList
            }
        }
    }

    fun startNewTrip(startKm: Double, purpose: String = "Business", location: String? = null) {
        val newTrip = TripEntity(
            date = LocalDate.now(),
            timeStart = java.time.LocalTime.now(),
            startKm = startKm,
            purpose = purpose,
            location = location
        )
        _currentTrip.value = newTrip
    }

    fun endCurrentTrip(endKm: Double, endLocation: String? = null, notes: String? = null) {
        val current = _currentTrip.value ?: return

        val completedTrip = current.copy(
            timeEnd = java.time.LocalTime.now(),
            endKm = endKm,
            endLocation = endLocation,
            notes = notes,
            distanceKm = current.calculateDistance()  // or recalculate properly
        )

        viewModelScope.launch {
            repository.insertTrip(completedTrip)
            _currentTrip.value = null
            loadTrips() // refresh list
        }
    }

    fun cancelCurrentTrip() {
        _currentTrip.value = null
    }
}