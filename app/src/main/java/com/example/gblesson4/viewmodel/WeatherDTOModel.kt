package com.example.gblesson4.viewmodel

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gblesson4.model.City
import com.example.gblesson4.model.RemoteRepository
import com.example.gblesson4.model.RemoteRepositoryImpl
import com.example.gblesson4.model.apikey.WeatherDTO
import com.example.gblesson4.utils.getLines
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Handler
import javax.net.ssl.HttpsURLConnection


class WeatherDTOModel (
    private val liveDataDTO: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RemoteRepository =  RemoteRepositoryImpl()
) : ViewModel() {

    fun getLiveDataDTO() = liveDataDTO

    fun getWeather(city: City) = getWeatherFromServer(city)


    private fun getWeatherFromServer(city: City) {
        liveDataDTO.value = AppState.Loading
        requestToServer(city)

    }

    private fun requestToServer(city: City) {
        val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val connection: HttpsURLConnection?

        try {
            connection = uri.openConnection() as HttpsURLConnection
            connection.readTimeout = 10000
            connection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val weather = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        liveDataDTO.postValue(AppState.SuccessFromServer(weather))
                    }
                } catch (e : Exception) {
                    liveDataDTO.postValue(AppState.Error(e))
                    Log.e("@@@", "Cannot receive data from server", e)
                    e.printStackTrace()
                } finally {
                    connection.disconnect()
                }
            }.start()
        } catch (e : MalformedURLException) {
            liveDataDTO.value = AppState.Error(e)
            Log.e("@@@", "Fail URI", e)
            e.printStackTrace()
        }
    }
}