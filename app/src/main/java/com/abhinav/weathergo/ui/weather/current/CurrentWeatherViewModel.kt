package com.abhinav.weathergo.ui.weather.current

import androidx.lifecycle.ViewModel
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.internal.lazyDeferred
import com.abhinav.weathergo.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val weatherGoRepository: WeatherGoRepository
) : WeatherViewModel(weatherGoRepository) {

    val weather by lazyDeferred {
        weatherGoRepository.getCurrentWeather()
    }
}
