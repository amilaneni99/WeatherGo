package com.abhinav.weathergo.data.network.response

import com.abhinav.weathergo.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName


data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)