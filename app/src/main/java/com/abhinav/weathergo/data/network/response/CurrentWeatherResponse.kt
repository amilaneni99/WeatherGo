package com.abhinav.weathergo.data.network.response

import com.abhinav.weathergo.data.db.entity.CurrentWeatherEntry
import com.abhinav.weathergo.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation
)