package com.example.gblesson4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gblesson4.model.repository.RoomRepositoryImpl

class WeatherHistoryModelFromRoom(
    private val liveData: MutableLiveData<AppStateRoom> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getAllWeather() = getAllWeatherFromRoom()

    private fun getAllWeatherFromRoom() {
        liveData.value = AppStateRoom.Loading
        val weatherModelFromRoom = RoomRepositoryImpl()
        Thread {
            liveData.postValue(AppStateRoom.Success(weatherModelFromRoom.getAllWeatherFromHistory()))
        }.start()
    }

}