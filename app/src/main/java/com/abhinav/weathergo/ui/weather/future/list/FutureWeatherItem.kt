package com.abhinav.weathergo.ui.weather.future.list

import com.abhinav.weathergo.R
import com.abhinav.weathergo.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.abhinav.weathergo.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
): Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.conditionText
            updateDate()
            textView_temperature.text = "${weatherEntry.avgTemperature}°C"
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate(){
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dateFormatter)
    }

    private fun ViewHolder.updateConditionImage(){
        GlideApp.with(this.containerView)
            .load("https:"+weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }



}