package com.rideshare.odometer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * AppDatabase: Room database for RideshareOdoMeter
 * 
 * Stores all trip data locally for ATO compliance
 * Data is never transmitted outside the device
 */
@Database(
    entities = [TripEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun tripDao(): TripDao
    
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        
        /**
         * Get singleton instance of AppDatabase
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        
        /**
         * Build the database
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rideshare_odo_meter.db"
            )
                .fallbackToDestructiveMigration() // Development: reset on schema change
                .build()
        }
        
        /**
         * Destroy database instance (for testing)
         */
        fun destroyInstance() {
            instance = null
        }
    }
}