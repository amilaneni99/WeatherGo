package com.abhinav.weathergo.data.db.unitLocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate
interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}