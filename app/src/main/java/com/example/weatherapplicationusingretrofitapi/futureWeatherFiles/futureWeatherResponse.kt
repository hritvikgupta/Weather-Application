package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles


import com.google.gson.annotations.SerializedName

data class futureWeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)