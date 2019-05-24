package com.abhinav.weathergo.data.repository

import androidx.lifecycle.LiveData
import com.abhinav.weathergo.data.db.CurrentWeatherDao
import com.abhinav.weathergo.data.db.FutureWeatherDao
import com.abhinav.weathergo.data.db.WeatherLocationDao
import com.abhinav.weathergo.data.db.entity.WeatherLocation
import com.abhinav.weathergo.data.db.unitLocalized.current.UnitSpecificCurrentWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.abhinav.weathergo.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.abhinav.weathergo.data.network.FORECAST_DAYS
import com.abhinav.weathergo.data.network.WeatherNetworkDataSource
import com.abhinav.weathergo.data.network.response.CurrentWeatherResponse
import com.abhinav.weathergo.data.network.response.FutureWeatherResponse
import com.abhinav.weathergo.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class WeatherGoRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider)
    : WeatherGoRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeatherMetric()
        }
    }

    override suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext futureWeatherDao.getSimpleForecastWeatherMetric(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(date: LocalDate): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDao.getDetailedWeatherByDateMetric(date)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {

        fun deleteOldForecastData(){
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){

        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
        if (isFetchFutureNeeded())
            fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language)
    }

    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(): Boolean{
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS
    }
}