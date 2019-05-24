package com.abhinav.weathergo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhinav.weathergo.data.db.entity.CURRENT_WEATHER_ID
import com.abhinav.weathergo.data.db.entity.CurrentWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>
}