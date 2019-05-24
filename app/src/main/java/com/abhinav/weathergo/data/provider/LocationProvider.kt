package com.abhinav.weathergo.data.provider

import com.abhinav.weathergo.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lasWeatherLocation: WeatherLocation):Boolean
    suspend fun getPreferredLocationString(): String
}