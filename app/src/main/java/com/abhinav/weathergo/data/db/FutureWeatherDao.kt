package com.abhinav.weathergo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhinav.weathergo.data.db.entity.FutureWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.detail.MetricDetailFutureWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleForecastWeatherMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailedWeatherByDateMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>
}