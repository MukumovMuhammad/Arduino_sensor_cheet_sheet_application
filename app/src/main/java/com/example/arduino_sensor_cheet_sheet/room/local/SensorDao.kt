package com.example.arduino_sensor_cheet_sheet.room.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorentity")
    fun getAll(): Flow<List<SensorEntity>>

    @Query("SELECT * FROM sensorentity WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<SensorEntity>

    @Query("SELECT * FROM sensorentity WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): SensorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sensors: SensorEntity)

    @Delete
    fun delete(sensor: SensorEntity)

    @Query("DELETE FROM sensorentity")
    fun deleteAll()

}
