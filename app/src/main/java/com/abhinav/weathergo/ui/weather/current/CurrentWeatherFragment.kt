package com.abhinav.weathergo.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.abhinav.weathergo.R
import com.abhinav.weathergo.data.network.ApixuWeatherApiService
import com.abhinav.weathergo.data.network.ConnectivityInterceptorImpl
import com.abhinav.weathergo.data.network.WeatherNetworkDataSource
import com.abhinav.weathergo.data.network.WeatherNetworkDataSourceImpl
import com.abhinav.weathergo.internal.glide.GlideApp
import com.abhinav.weathergo.ui.base.ScopedFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }
    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        currentWeather.observe(this@CurrentWeatherFragment, Observer {

            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            updateDatetoToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecip(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)

            System.out.println(it.conditionIconUrl)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionIconUrl}")
                .into(imageView_condition_icon)

        })

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDatetoToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateTemperatures(temperature: Double,feelsLike: Double){
        val unit = "°C"
        textView_temperature.text = "$temperature$unit"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unit"
    }
    private fun updatePrecip(precipitationVolume: Double){
        val unit = "mm"
        textView_precipitation.text = "Precipitation: $precipitationVolume$unit"
    }
    private fun updateCondition(conditon: String){
        textView_condition.text = conditon
    }
    private fun updateWind(windDirection: String, windSpeed: Double){
        textView_wind.text = "Wind: $windDirection, $windSpeed Km/h"
    }


}
