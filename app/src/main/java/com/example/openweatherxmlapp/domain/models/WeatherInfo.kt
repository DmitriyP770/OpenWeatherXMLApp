package com.example.openweatherxmlapp.domain.models

import java.time.LocalDateTime


data class WeatherInfo(
    val time: LocalDateTime,
    val temperature: Double,
    val weatherCode: Int,
    val windSpeed:Double,
    val pressure: Double,
    val humidity:Double,
    val weatherType: WeatherType
){


}