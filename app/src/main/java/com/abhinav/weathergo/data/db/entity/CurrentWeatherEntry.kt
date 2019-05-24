package com.abhinav.weathergo.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}