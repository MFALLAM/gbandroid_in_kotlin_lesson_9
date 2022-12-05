package com.example.gblesson4.viewmodel

import com.example.gblesson4.model.Weather

sealed class AppStateRoom {
    data class Success(val weather: List<Weather>) : AppStateRoom()
    data class Error(val error: Throwable) : AppStateRoom()
    object Loading : AppStateRoom()
}