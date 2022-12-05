package com.example.gblesson4.model.repository

import com.example.gblesson4.model.dto.WeatherDTO

class RetrofitRepositoryImpl(private val remoteDataSource: RemoteDataSource): RetrofitDetailsRepository {
    override fun getWeather(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}