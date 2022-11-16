package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles


import com.google.gson.annotations.SerializedName

data class Forecastday(
    val astro: Astro,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: Day,
    val hour: List<Hour>
)