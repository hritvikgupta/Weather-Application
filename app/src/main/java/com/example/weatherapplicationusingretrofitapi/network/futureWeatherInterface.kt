package com.example.weatherapplicationusingretrofitapi.network

import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.futureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY2 = "f360669ec6864884a24141403221311"
//http://api.weatherapi.com/v1/forecast.json?key=b06d51ae4ec6409e979160137223010&q=London&days=2&aqi=no&alerts=no

//URL = http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
interface futureWeatherInterface
{
    @GET("forecast.json")
    fun getLongLat(
        @Query("q") q: String,
        @Query("days") days:String
    ):Deferred<futureWeatherResponse>

    companion object{
        operator fun invoke() : futureWeatherInterface{
            val requestInterceptor = Interceptor { chain ->
                val url = chain .request().url().newBuilder().addQueryParameter("key", API_KEY2).build()
                val request = chain.request().newBuilder().url(url).build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .build()
            return  Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
                .create(futureWeatherInterface::class.java)
        }
    }

}

