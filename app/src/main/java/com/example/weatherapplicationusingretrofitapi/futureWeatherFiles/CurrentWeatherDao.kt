package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.ImperialCurrentWeatherEntry
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.MetricsCurrentWeatherEntry
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.WeatherData
import com.example.weatherapplicationusingretrofitapi.network.futureWeatherInterface

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * from weatherData")
    fun getWeatherMetric() : LiveData<List<WeatherData>>

    @Insert(onConflict = REPLACE)
    fun insertWeather(weatherData:WeatherData)

    @Update
    fun update(items: WeatherData)


}