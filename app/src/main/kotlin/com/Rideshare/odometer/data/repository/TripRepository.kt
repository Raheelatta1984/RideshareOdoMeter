package com.rideshare.odometer.data.repository

import com.rideshare.odometer.data.AppDatabase
import com.rideshare.odometer.data.TripEntity
import kotlinx.coroutines.flow.Flow

class TripRepository(private val database: AppDatabase) {

        private val tripDao = database.tripDao()

            // Get all trips
                fun getAllTrips(): Flow<List<TripEntity>> = tripDao.getAllTrips()

                    // Get trips by date range (useful for ATO 12-week report)
                        fun getTripsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TripEntity>> =
                                tripDao.getTripsByDateRange(startDate, endDate)

                                    suspend fun insertTrip(trip: TripEntity) {
                                                tripDao.insertTrip(trip)
                                    }

                                        suspend fun updateTrip(trip: TripEntity) {
                                                    tripDao.updateTrip(trip)
                                        }

                                            suspend fun deleteTrip(trip: TripEntity) {
                                                        tripDao.deleteTrip(trip)
                                            }

                                                suspend fun getTripById(id: Long): TripEntity? = tripDao.getTripById(id)
}
                                            }
                                        }
                                    }
}