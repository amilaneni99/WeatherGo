package com.abhinav.weathergo.data.provider

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.abhinav.weathergo.data.db.entity.WeatherLocation
import com.abhinav.weathergo.internal.LocationPermissionsNotGrantedException
import com.abhinav.weathergo.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import java.util.jar.Manifest

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lasWeatherLocation: WeatherLocation): Boolean {

        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lasWeatherLocation)
        }catch (e: LocationPermissionsNotGrantedException){
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lasWeatherLocation)
    }

    private fun hasCustomLocationChanged(lasWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()){
            val customLocationName = getCustomLocationName()
            return customLocationName != lasWeatherLocation.name
        }
        return false
    }

    private fun getCustomLocationName(): String?{
        return preferences.getString(CUSTOM_LOCATION,null)
    }

    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            }catch (e: LocationPermissionsNotGrantedException){
                return "${getCustomLocationName()}"
            }
        }
        else
            return "${getCustomLocationName()}"
    }

    private suspend fun hasDeviceLocationChanged(lasWeatherLocation: WeatherLocation): Boolean {

        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03
        return  Math.abs(deviceLocation.latitude - lasWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lasWeatherLocation.lon) > comparisonThreshold

    }

    private fun isUsingDeviceLocation(): Boolean{
        return preferences.getBoolean(USE_DEVICE_LOCATION,true)
    }

    private fun getLastDeviceLocation(): Deferred<Location?>{
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionsNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


}