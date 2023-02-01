package com.example.openweatherxmlapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherxmlapp.data.location.LocationTracker
import com.example.openweatherxmlapp.domain.models.WeatherData
import com.example.openweatherxmlapp.domain.repository.WeatherRepository
import com.example.openweatherxmlapp.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val repository : WeatherRepository,
   private val locationTracker: LocationTracker
): ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherEvents>(WeatherEvents.Empty)
    val weatherState: StateFlow<WeatherEvents>
        get() = _weatherState

    sealed class WeatherEvents{
        object Loading : WeatherEvents()
        object Empty : WeatherEvents()
        class Success (val weatherData : WeatherData) : WeatherEvents()
        class Failure(val message: String) : WeatherEvents()
    }

    init {

    }

    private fun getWeatherInfo(lat: Double, long : Double){
        viewModelScope.launch {
           locationTracker.getCurrentLocation()?.let { location ->
               _weatherState.value = WeatherEvents.Loading
               repository.getWeatherInfo(location.latitude, location.longitude).collect{
                   when(it){
                       is Resource.Error -> {
                           _weatherState.value = WeatherEvents.Failure(
                               message = it.message ?: "Unknown error"
                           )
                       }
                       is Resource.Success -> {
                           _weatherState.value = WeatherEvents.Success(
                               weatherData = it.data!!
                           )
                       }
                       else ->{
                           _weatherState.value = WeatherEvents.Loading
                       }
                   }
           }

            } ?: kotlin.run {
                _weatherState.value = WeatherEvents.Failure(
                    message = "Couldn't retrieve location"
                )
           }
        }

    }


}