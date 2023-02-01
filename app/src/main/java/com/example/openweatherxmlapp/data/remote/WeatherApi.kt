package com.example.openweatherxmlapp.data.remote

import com.example.openweatherxmlapp.util.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //get weather forecast for 3 days
    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherInfo(
        @Query("latitude") lat: Double ,
        @Query("longitude") long: Double
    )
    : Response<WeatherInfoDto>
}