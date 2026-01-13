package com.example.arduino_sensor_cheet_sheet.room.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SensorEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "context") val context: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "title_img") val title_img: String?,
    @ColumnInfo(name = "scheme_img") val scheme_img: String?
)



