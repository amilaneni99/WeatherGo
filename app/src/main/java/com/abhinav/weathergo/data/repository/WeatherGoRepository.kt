package com.abhinav.weathergo.data.repository

import androidx.lifecycle.LiveData
import com.abhinav.weathergo.data.db.entity.WeatherLocation
import com.abhinav.weathergo.data.db.unitLocalized.current.UnitSpecificCurrentWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface WeatherGoRepository {
    suspend fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherByDate(date: LocalDate): LiveData<out UnitSpecificDetailFutureWeatherEntry>
}