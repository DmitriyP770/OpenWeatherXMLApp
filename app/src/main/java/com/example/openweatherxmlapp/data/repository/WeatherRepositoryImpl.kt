package com.example.openweatherxmlapp.data.repository

import com.example.openweatherxmlapp.data.local.WeatherDataBase
import com.example.openweatherxmlapp.data.remote.WeatherApi
import com.example.openweatherxmlapp.domain.models.WeatherData
import com.example.openweatherxmlapp.domain.repository.WeatherRepository
import com.example.openweatherxmlapp.mappers.toWeatherData
import com.example.openweatherxmlapp.mappers.toWeatherInfoEntity
import com.example.openweatherxmlapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api : WeatherApi,
    private val db: WeatherDataBase

) : WeatherRepository {

    override suspend fun getWeatherInfo(
        lat : Double ,
        long : Double ,
    ) : Flow<Resource<WeatherData>> {
        return flow<Resource<WeatherData>> {
            emit(Resource.Loading(true))
            val localWeatherInfo = db.weatherDao.getWeatherInfo()
            //if we alredy have something in db, then
            localWeatherInfo?.let {
                emit(Resource.Success(it.toWeatherData()))
                try {
                    val response = api.getWeatherInfo(lat, long)
                    if (response.isSuccessful){
                        db.weatherDao.clearWeatherInfo()
                        val remoteData = response.body()?.let {
                            db.weatherDao.clearWeatherInfo()
                            db.weatherDao.insertWeatherData(it.toWeatherInfoEntity())
                            val newLocalData = db.weatherDao.getWeatherInfo()
                            emit(Resource.Success(data = newLocalData?.toWeatherData()))
                            return@flow
                        }

                    }
                } catch (e: Exception){
                    e.printStackTrace()
                    emit(Resource.Error(e.message ?: "Something went wrong"))
                }
            }
            try {
                val response = api.getWeatherInfo(lat, long)
                if (response.isSuccessful){
                    val remoteData = response.body()
                    remoteData?.toWeatherInfoEntity()?.let { db.weatherDao.insertWeatherData(it) }
                    val localData  = db.weatherDao.getWeatherInfo()
                    emit(Resource.Success(data = localData?.toWeatherData()))
                }
            } catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }
}