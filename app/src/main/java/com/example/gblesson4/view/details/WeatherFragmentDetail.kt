package com.example.gblesson4.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gblesson4.R
import com.example.gblesson4.databinding.FragmentWeatherDetailBinding
import com.example.gblesson4.model.Weather


class WeatherFragmentDetail : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherFragmentDetail {
            return WeatherFragmentDetail().apply { arguments = bundle }
        }
    }

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
            weather.city.also { city ->
                with(binding) {
                    cityName.text = city.name
                    cityCoordinates.text = String.format(
                        getString(R.string.city_coordinates),
                        city.lat.toString(),
                        city.lon.toString()
                    )
                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeValue.text = weather.feelsLike.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}