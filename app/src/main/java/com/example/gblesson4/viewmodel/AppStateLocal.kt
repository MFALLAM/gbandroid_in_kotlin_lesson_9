package com.example.gblesson4.viewmodel

import com.example.gblesson4.model.Weather

sealed class AppStateLocal {
    data class Success(val weatherData: List<Weather>) : AppStateLocal()
    data class Error(val error: Throwable) : AppStateLocal()
    object Loading : AppStateLocal()
}