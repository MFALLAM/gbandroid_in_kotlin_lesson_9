package com.example.gblesson4.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_table WHERE name = :cityName")
    fun getWeatherByCity(cityName: String): List<WeatherEntity>

    @Query("SELECT * FROM weather_table WHERE lat = :lat AND lon = :lon")
    fun getWeatherByCoordinates(lat: Double, lon: Double): List<WeatherEntity>

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): List<WeatherEntity>
}