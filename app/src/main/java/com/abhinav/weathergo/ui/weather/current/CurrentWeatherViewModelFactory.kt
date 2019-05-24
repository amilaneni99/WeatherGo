package com.abhinav.weathergo.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhinav.weathergo.data.repository.WeatherGoRepository

class CurrentWeatherViewModelFactory(
    private val weatherGoRepository: WeatherGoRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherGoRepository) as T
    }
}