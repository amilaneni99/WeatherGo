package com.abhinav.weathergo.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException : IOException()
class LocationPermissionsNotGrantedException: Exception()
class DateNotFoundException: Exception()