package com.example.openweatherxmlapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [WeatherInfoEntity::class], version = 1)
abstract class WeatherDataBase : RoomDatabase(){

    abstract val weatherDao: WeatherDao
}