package com.example.gblesson4.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gblesson4.R
import com.example.gblesson4.databinding.FragmentWeatherDetailBinding
import com.example.gblesson4.model.Weather
import com.example.gblesson4.viewmodel.AppState
import com.example.gblesson4.viewmodel.WeatherDTOModel
import com.google.android.material.snackbar.Snackbar

class WeatherFragmentDetails : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherFragmentDetails {
            return WeatherFragmentDetails().apply { arguments = bundle }
        }
    }

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private var city: City = Weather().city // default city

    private val viewModel: WeatherDTOModel by lazy {
        ViewModelProvider(this)[WeatherDTOModel::class.java]
    }

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
            city = weather.city
        }

        viewModel.getLiveDataDTO().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(city)
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun renderData(appState: AppState) = when (appState) {
        is AppState.SuccessFromServer -> {
            with(binding) {
                cityName.text = city.name
                appState.weatherDTO.fact.let {
                    temperatureValue.text = it.temp.toString()
                    feelsLikeValue.text = it.feelsLike.toString()
                    weatherIcon.loadSVG("$YANDEX_WEATHER_ICON${it.icon}.svg")
                }
            }
        }
        is AppState.Error -> {
            Snackbar
                .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload") { viewModel.getWeather(city) }
                .show()
        }
        else -> {}
    }.also {
        if (appState == AppState.Loading) binding.loadingLayout.visibility = View.VISIBLE
        else binding.loadingLayout.visibility = View.GONE
    }

    private fun AppCompatImageView.loadSVG(url: String) {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(this.context)
            .placeholder(R.drawable.loading)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}