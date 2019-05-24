package com.abhinav.weathergo.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String?) = str?.let {
        org.threeten.bp.LocalDate.parse(it,org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: org.threeten.bp.LocalDate?) = dateTime?.format(org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE)
}