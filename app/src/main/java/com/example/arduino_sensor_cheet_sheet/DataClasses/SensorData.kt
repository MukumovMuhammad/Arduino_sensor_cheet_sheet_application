package com.example.arduino_sensor_cheet_sheet.DataClasses

import com.example.arduino_sensor_cheet_sheet.room.data.SensorDto

data class SensorData(
    val items: List<SensorDto>,
    val message: String,
    val status: Boolean
)