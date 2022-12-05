package com.example.gblesson4.viewmodel

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gblesson4.model.City
import com.example.gblesson4.model.Weather
import com.example.gblesson4.model.dto.WeatherDTO
import com.example.gblesson4.model.repository.RemoteDataSource
import com.example.gblesson4.model.repository.RetrofitRepositoryImpl
import com.example.gblesson4.utils.REQUEST_ERROR
import com.example.gblesson4.utils.SERVER_ERROR
import com.example.gblesson4.utils.convertDtoToWeather
import com.example.gblesson4.utils.getLines
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Handler
import javax.net.ssl.HttpsURLConnection


class WeatherModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) = getWeatherFromServer(city)

    fun setWeather(weather: Weather) {
        liveData.postValue(AppState.Success(weather))
    }


    private fun getWeatherFromServer(city: City) {
        liveData.value = AppState.Loading
        val detailsRepositoryImpl = RetrofitRepositoryImpl(RemoteDataSource())
        detailsRepositoryImpl.getWeather(city.lat, city.lon, callBack)
    }

    private val callBack = object : retrofit2.Callback<WeatherDTO> {
        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {
            val serverResponse: WeatherDTO? = response.body()

            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.Success(convertDtoToWeather(serverResponse))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }
}