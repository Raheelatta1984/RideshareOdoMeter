package com.rideshare.odometer

import android.app.Application
import com.rideshare.odometer.data.AppDatabase
import com.rideshare.odometer.data.repository.TripRepository

/**
 * OdoMeterApplication: Custom Application class for app-wide initialization
 * 
 * Initializes:
 * - Database instance
 * - Repository instances
 * - Global configurations
 */
class OdoMeterApplication : Application() {
    
    companion object {
        lateinit var appInstance: OdoMeterApplication
            private set
        
        val tripRepository: TripRepository by lazy {
            TripRepository(AppDatabase.getInstance(appInstance))
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        
        // Initialize database
        val database = AppDatabase.getInstance(this)
        database.tripDao() // Ensure database is created
    }
}
