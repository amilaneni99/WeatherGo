package com.abhinav.weathergo.data.db.entity


import androidx.room.Embedded
import com.abhinav.weathergo.data.db.entity.Condition
import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,
    val uv: Double
)