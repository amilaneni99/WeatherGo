package com.abhinav.weathergo.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import org.threeten.bp.LocalDate

class FutureWeatherViewModelFactory(
    private val detailDate: LocalDate,
    private val weatherGoRepository: WeatherGoRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate,weatherGoRepository) as T
    }
}