package com.example.openweatherxmlapp.data.location

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?
}