package com.abhinav.weathergo

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.abhinav.weathergo.data.db.WeatherGoDatabase
import com.abhinav.weathergo.data.network.*
import com.abhinav.weathergo.data.provider.LocationProvider
import com.abhinav.weathergo.data.provider.LocationProviderImpl
import com.abhinav.weathergo.data.repository.WeatherGoRepository
import com.abhinav.weathergo.data.repository.WeatherGoRepositoryImpl
import com.abhinav.weathergo.ui.weather.current.CurrentWeatherViewModelFactory
import com.abhinav.weathergo.ui.weather.future.detail.FutureWeatherViewModelFactory
import com.abhinav.weathergo.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class WeatherGoApp : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherGoApp))

        bind() from singleton { WeatherGoDatabase(instance()) }
        bind() from singleton { instance<WeatherGoDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherGoDatabase>().futureWeatherDao() }
        bind() from singleton { instance<WeatherGoDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<WeatherGoRepository>() with singleton { WeatherGoRepositoryImpl(instance(),instance(),instance(),instance(),instance()) }
        bind() from  singleton { CurrentWeatherViewModelFactory(instance()) }
        bind() from  singleton { FutureListWeatherViewModelFactory(instance()) }
        bind() from factory { detailDate: LocalDate -> FutureWeatherViewModelFactory(detailDate, instance()) }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }

}