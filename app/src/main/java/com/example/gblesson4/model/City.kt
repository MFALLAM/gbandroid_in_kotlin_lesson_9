package com.example.gblesson4.model


import android.location.Geocoder
import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import com.example.gblesson4.MyGbApp
import java.io.IOException

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) : Parcelable

// FIXME: проверка permissions всегда перед использованием!!!!
fun getAddress(lat: Double, lng: Double): String {
    val geocoder = Geocoder(MyGbApp.appContext, MyGbApp.appContext.resources.configuration.locales.get(0))
    var currentLocation: String
    try {
        val list = geocoder.getFromLocation(lat, lng, 1)
        currentLocation = list[0].locality
    } catch (e: IOException) {
        currentLocation = "Unknown"
        Log.d("@@@", e.toString())
    }

    return currentLocation
}