package com.example.openweatherxmlapp.data.local

import androidx.room.Insert
import androidx.room.Query

interface WeatherDao {

    @Insert
    suspend fun insertWeatherData(weatherInfoEntity : WeatherInfoEntity)

    @Query("SELECT * FROM weather_table")
    suspend fun getWeatherInfo(): WeatherInfoEntity?

    @Query("DELETE FROM weather_table")
    suspend fun clearWeatherInfo()
}