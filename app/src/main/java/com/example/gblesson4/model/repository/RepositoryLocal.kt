package com.example.gblesson4.model.repository

import com.example.gblesson4.model.Location
import com.example.gblesson4.model.Weather
import com.example.gblesson4.model.getRussianCities
import com.example.gblesson4.model.getWorldCities

fun interface RepositoryWeatherFromLocal {
    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}


fun interface RepositoryWeatherFromLocal {
    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}


class RepositoryLocalImpl : RepositoryWeatherFromLocal {
    override fun getWeather(
        hasInternet: Boolean,
        location: Location
    ): List<Weather> = when (hasInternet) {
        true -> getWeatherFromServer()
        else -> when (location) {
            Location.World -> getWeatherFromLocalSourceWorld()
            Location.Russia -> getWeatherFromLocalSourceRus()
        }}

    private fun getWeatherFromServer(): List<Weather> = listOf(Weather())

    private fun getWeatherFromLocalSourceRus(): List<Weather> = getRussianCities()

    private fun getWeatherFromLocalSourceWorld(): List<Weather> = getWorldCities()
}