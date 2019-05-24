package com.abhinav.weathergo.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.internal.lazyDeferred
import com.abhinav.weathergo.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val weatherGoRepository: WeatherGoRepository
): WeatherViewModel(weatherGoRepository) {
    val weatherEntries by lazyDeferred{
        weatherGoRepository.getFutureWeatherList(LocalDate.now())
    }
}
