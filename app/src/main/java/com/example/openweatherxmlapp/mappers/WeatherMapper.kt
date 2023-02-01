package com.example.openweatherxmlapp.mappers

import com.example.openweatherxmlapp.data.local.WeatherInfoEntity
import com.example.openweatherxmlapp.data.remote.WeatherInfoDto
import com.example.openweatherxmlapp.domain.models.WeatherData
import com.example.openweatherxmlapp.domain.models.WeatherInfo
import com.example.openweatherxmlapp.domain.models.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherInfo
)

fun WeatherInfoEntity.toWeatherData() : WeatherData {
    val map =  time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherInfo(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity.toDouble(),
                weatherCode = weatherCode,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
    val now = LocalDateTime.now()
    val currentData = map[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherData(
        forecastForToday = map.getValue(0),
        forecastForTomorrow = map.getValue(1),
        forecastForAfterTomorrow = map.getValue(2),
        currentWeatherInfo = currentData
    )
}

fun WeatherInfoDto.toWeatherInfoEntity() = WeatherInfoEntity(
    time = time ?: emptyList() ,
    temperatures = temperatures ?: emptyList() ,
    weatherCodes = weatherCodes ?: emptyList() ,
    windSpeeds = windSpeeds ?: emptyList() ,
    pressures = pressures ?: emptyList() ,
    humidities = humidities ?: emptyList()
)