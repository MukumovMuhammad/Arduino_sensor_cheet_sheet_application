package com.example.arduino_sensor_cheet_sheet.room.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorentity")
    fun getAll(): List<SensorEntity>

    @Query("SELECT * FROM sensorentity WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<SensorEntity>

    @Query("SELECT * FROM sensorentity WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): SensorEntity

    @Insert
    fun insertAll(vararg sensors: SensorEntity)

    @Delete
    fun delete(sensor: SensorEntity)

    @Query("DELETE FROM sensorentity")
    fun deleteAll()

}
