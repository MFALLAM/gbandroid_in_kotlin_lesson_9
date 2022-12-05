package com.example.gblesson4.model.repository

import com.example.gblesson4.model.Weather

interface RoomDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: ResponseCallback
    )
}

interface AllWeatherFromRoom {
    fun getAllWeatherFromHistory(): List<Weather>
}

interface ResponseCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: Exception)
}