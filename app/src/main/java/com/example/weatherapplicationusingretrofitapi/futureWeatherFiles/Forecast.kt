package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles


import com.google.gson.annotations.SerializedName

data class Forecast(
    val forecastday: List<Forecastday>
)