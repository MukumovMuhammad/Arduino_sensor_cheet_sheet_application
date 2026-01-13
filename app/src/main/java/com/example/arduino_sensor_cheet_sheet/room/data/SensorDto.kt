package com.example.arduino_sensor_cheet_sheet.room.data

import com.example.arduino_sensor_cheet_sheet.room.local.SensorEntity

data class SensorDto(
    val id: Int,
    val code: String,
    val context: String,
    val title: String,
    val title_img: String,
    val scheme_img: String
)

fun SensorDto.toEntity() = SensorEntity(
    uid = id,
    code = code,
    context = context,
    title = title,
    title_img = title_img,
    scheme_img = scheme_img
)