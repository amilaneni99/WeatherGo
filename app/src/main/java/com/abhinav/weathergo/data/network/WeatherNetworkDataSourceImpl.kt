package com.abhinav.weathergo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhinav.weathergo.data.network.response.CurrentWeatherResponse
import com.abhinav.weathergo.data.network.response.FutureWeatherResponse
import com.abhinav.weathergo.internal.NoConnectivityException

const val FORECAST_DAYS = 7

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location,languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection.", e)
        }
    }

    override suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    ) {
        try {
            val fetchedFutureWeather = apixuWeatherApiService
                .getFutureWeather(location, FORECAST_DAYS,languageCode)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection",e)
        }
    }
}