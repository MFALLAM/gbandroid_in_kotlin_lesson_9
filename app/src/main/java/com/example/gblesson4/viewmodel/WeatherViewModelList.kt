package com.example.gblesson4.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gblesson4.model.*
import com.example.gblesson4.model.repository.RepositoryLocalImpl
import com.example.gblesson4.model.repository.RepositoryWeatherFromLocal
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherViewModelList(
    private val liveData: MutableLiveData<AppStateLocal> = MutableLiveData(),
    private val repository: RepositoryWeatherFromLocal = RepositoryLocalImpl()
): ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(location: Location) = getDataFromLocalSourceList(location)

    private fun getDataFromLocalSourceList(location: Location) {
        liveData.value = AppStateLocal.Success(repository.getWeather(false, location))
    }
}