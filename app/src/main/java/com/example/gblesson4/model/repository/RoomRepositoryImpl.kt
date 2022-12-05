package com.example.gblesson4.model.repository


class RoomRepositoryImpl: RoomDetailsRepository, RoomInsertWeather, AllWeatherFromRoom {
    override fun getWeather(lat: Double, lon: Double, callback: ResponseCallback) {
        callback.onResponse(WeatherDatabase.invoke(MyApp.appContext)
            .weatherDao()
            .getWeatherByCoordinates(lat, lon).let {
                convertEntityToWeather(it).last()
            })
    }

    override fun saveWeather(weather: Weather) {
        Thread {
            WeatherDatabase.invoke(MyApp.appContext).weatherDao()
                .insert(convertWeatherToEntity(weather))
        }.start()
    }

    override fun getAllWeatherFromHistory(): List<Weather> {
        return convertEntityToWeather(WeatherDatabase.invoke(MyApp.appContext).weatherDao().getAllWeather())
    }
}