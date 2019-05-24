package com.abhinav.weathergo.data.db.unitLocalized.future.detail

import org.threeten.bp.LocalDate

interface UnitSpecificDetailFutureWeatherEntry {
    val date: LocalDate
    val maxTemperature: Double
    val minTemperature: Double
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val maxWindSpeed: Double
    val totalPrecipitation: Double
}