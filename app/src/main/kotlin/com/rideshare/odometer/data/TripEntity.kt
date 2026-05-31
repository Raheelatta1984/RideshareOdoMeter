package com.rideshare.odometer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

/**
 * TripEntity: Room database table for storing rideshare trips
 * 
 * ATO Compliance Requirements:
 * - Tracks trips for 12-week representative period
 * - Stores start/end odometer readings (validated: endKm > startKm)
 * - Default trip purpose: "Business" (ATO requirement)
 * - Local storage only (privacy-first approach)
 */
@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Trip Date & Time
    val date: LocalDate,
    val timeStart: LocalTime? = null,
    val timeEnd: LocalTime? = null,
    
    // Odometer Readings (km)
    val startKm: Double,
    val endKm: Double,
    
    // Trip Details
    val purpose: String = "Business", // ATO default: Business trips
    val location: String? = null,     // Start location (suburb)
    val endLocation: String? = null,  // End location (suburb)
    val postcode: String? = null,     // Postcode for suburb tracking
    
    // Distance Calculation
    val distanceKm: Double = 0.0,     // Calculated: endKm - startKm
    
    // Optional Notes
    val notes: String? = null,
    
    // Data Integrity
    val isVerified: Boolean = false,  // Driver confirms accuracy
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    
    /**
     * Calculate trip distance with validation
     * ATO Requirement: End KM must be greater than Start KM
     */
    fun calculateDistance(): Double {
        return if (endKm > startKm) {
            endKm - startKm
        } else {
            0.0 // Invalid trip
        }
    }
    
    /**
     * Validate trip data before saving to database
     */
    fun isValid(): Boolean {
        return endKm > startKm && 
               startKm >= 0 && 
               endKm > 0 &&
               purpose.isNotBlank()
    }
    
    /**
     * Purpose categories for ATO compliance
     */
    companion object {
        const val PURPOSE_BUSINESS = "Business"
        const val PURPOSE_PERSONAL = "Personal"
        
        fun isValidPurpose(purpose: String): Boolean {
            return purpose in listOf(PURPOSE_BUSINESS, PURPOSE_PERSONAL)
        }
    }
}