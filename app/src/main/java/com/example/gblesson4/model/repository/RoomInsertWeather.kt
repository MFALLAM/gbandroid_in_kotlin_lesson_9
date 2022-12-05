package com.example.gblesson4.model.repository

import com.example.gblesson4.model.Weather


fun interface RoomInsertWeather {
    fun saveWeather(weather: Weather)
}