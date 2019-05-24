package com.abhinav.weathergo.ui.base

import androidx.lifecycle.ViewModel
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.internal.lazyDeferred

abstract class WeatherViewModel(
    private val weatherGoRepository: WeatherGoRepository
): ViewModel(){
    val weatherLocation by lazyDeferred{
        weatherGoRepository.getWeatherLocation()
    }
}