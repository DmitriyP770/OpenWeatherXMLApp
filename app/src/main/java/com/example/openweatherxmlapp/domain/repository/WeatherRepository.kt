package com.example.openweatherxmlapp.domain.repository

import com.example.openweatherxmlapp.domain.models.WeatherData
import com.example.openweatherxmlapp.domain.models.WeatherInfo
import com.example.openweatherxmlapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    // get weather info from remote api and then load it into db
    suspend fun getWeatherInfo(lat: Double, long: Double): Flow<Resource<WeatherData>>

}