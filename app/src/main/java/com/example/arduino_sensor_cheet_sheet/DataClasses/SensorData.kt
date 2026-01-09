package com.example.arduino_sensor_cheet_sheet.DataClasses

data class SensorData(
    val items: List<Item>,
    val message: String,
    val status: Boolean
)