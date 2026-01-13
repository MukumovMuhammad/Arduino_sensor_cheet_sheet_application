package com.example.arduino_sensor_cheet_sheet.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.arduino_sensor_cheet_sheet.room.local.SensorDao
import com.example.arduino_sensor_cheet_sheet.room.local.SensorEntity

@Database(entities = [SensorEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sensorDao(): SensorDao
}