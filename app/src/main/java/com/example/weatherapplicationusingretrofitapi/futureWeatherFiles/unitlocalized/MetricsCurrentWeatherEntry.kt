package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.CURRENT_WEATHER_ID

@Entity(tableName = "metricData")
data class MetricsCurrentWeatherEntry(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windMph")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "visMiles")
    override val visibilityDistance: Double
):unitSpecificCurrentWeatherEntry{@PrimaryKey(autoGenerate = false)
var id:Int = CURRENT_WEATHER_ID
}

