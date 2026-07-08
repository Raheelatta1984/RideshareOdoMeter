package com.rideshare.odometer.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * DateTimeConverters: Room TypeConverters for LocalDate and LocalTime
 * 
 * Converts between Room-compatible types (Long/String) and Java 8 time types
 */
class DateTimeConverters {
    
    companion object {
        private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    }
    
    /**
     * Convert LocalDate to String for Room storage
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }
    
    /**
     * Convert String back to LocalDate
     */
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }
    
    /**
     * Convert LocalTime to String for Room storage
     */
    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(timeFormatter)
    }
    
    /**
     * Convert String back to LocalTime
     */
    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, timeFormatter) }
    }
}
