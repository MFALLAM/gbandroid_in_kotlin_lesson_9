package com.example.gblesson4.model.repository

import com.example.gblesson4.model.dto.WeatherDTO

interface RetrofitDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}