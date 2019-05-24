package com.abhinav.weathergo.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.ui.weather.current.CurrentWeatherViewModel


class FutureListWeatherViewModelFactory(
    private val weatherGoRepository: WeatherGoRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            weatherGoRepository
        ) as T
    }
}