package com.abhinav.weathergo.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.internal.lazyDeferred
import com.abhinav.weathergo.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val weatherGoRepository: WeatherGoRepository
) : WeatherViewModel(weatherGoRepository) {
    val weather by lazyDeferred{
        weatherGoRepository.getFutureWeatherByDate(detailDate)
    }
}
