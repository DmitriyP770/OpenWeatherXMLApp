package com.example.openweatherxmlapp.domain.models

typealias WeatherDataMap = Map<Int, List<WeatherInfo>>

data class WeatherData(
    val currentWeatherInfo : WeatherInfo?,
    val forecastForToday: List<WeatherInfo>,
    val forecastForTomorrow: List<WeatherInfo>,
    val forecastForAfterTomorrow: List<WeatherInfo>

)
