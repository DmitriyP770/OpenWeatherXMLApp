package com.example.openweatherxmlapp.data.local

import androidx.room.Entity

@Entity(tableName = "weather_table")
data class WeatherInfoEntity(
    val id: Int? = null,
    val time: List<String>,
    val temperatures: List<Double>,
    val weatherCodes: List<Int>,
    val windSpeeds: List<Double>,
    val pressures: List<Double>,
    val humidities: List<Int>


)
