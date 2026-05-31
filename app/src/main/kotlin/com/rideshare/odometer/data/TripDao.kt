package com.rideshare.odometer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * TripDao: Data Access Object for trip operations
 * Provides database queries for CRUD operations on trips
 */
@Dao
interface TripDao {
    
    /**
     * Insert a new trip into the database
     */
    @Insert
    suspend fun insertTrip(trip: TripEntity): Long
    
    /**
     * Update existing trip
     */
    @Update
    suspend fun updateTrip(trip: TripEntity)
    
    /**
     * Delete a trip
     */
    @Delete
    suspend fun deleteTrip(trip: TripEntity)
    
    /**
     * Get all trips (real-time updates via Flow)
     */
    @Query("SELECT * FROM trips ORDER BY date DESC, timeStart DESC")
    fun getAllTrips(): Flow<List<TripEntity>>
    
    /**
     * Get trip by ID
     */
    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Long): TripEntity?
    
    /**
     * Get trips for a specific date
     */
    @Query("SELECT * FROM trips WHERE date = :date ORDER BY timeStart DESC")
    suspend fun getTripsByDate(date: LocalDate): List<TripEntity>
    
    /**
     * Get trips within date range (for ATO 12-week period)
     */
    @Query("SELECT * FROM trips WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTripsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TripEntity>>
    
    /**
     * Get verified trips only
     */
    @Query("SELECT * FROM trips WHERE isVerified = 1 ORDER BY date DESC")
    fun getVerifiedTrips(): Flow<List<TripEntity>>
    
    /**
     * Get trips by purpose (Business/Personal for ATO tracking)
     */
    @Query("SELECT * FROM trips WHERE purpose = :purpose ORDER BY date DESC")
    fun getTripsByPurpose(purpose: String): Flow<List<TripEntity>>
    
    /**
     * Calculate total distance for a date range
     */
    @Query("SELECT SUM(distanceKm) FROM trips WHERE date BETWEEN :startDate AND :endDate AND purpose = 'Business'")
    suspend fun getTotalBusinessDistance(startDate: LocalDate, endDate: LocalDate): Double?
    
    /**
     * Get trip count for statistics
     */
    @Query("SELECT COUNT(*) FROM trips WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getTripCount(startDate: LocalDate, endDate: LocalDate): Int
    
    /**
     * Delete all unverified trips (cleanup)
     */
    @Query("DELETE FROM trips WHERE isVerified = 0")
    suspend fun deleteUnverifiedTrips(): Int
    
    /**
     * Export trips as list
     */
    @Query("SELECT * FROM trips ORDER BY date DESC")
    suspend fun exportAllTrips(): List<TripEntity>
}