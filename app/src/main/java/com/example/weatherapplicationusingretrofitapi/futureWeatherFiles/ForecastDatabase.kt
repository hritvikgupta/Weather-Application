package com.example.weatherapplicationusingretrofitapi.futureWeatherFiles

import android.content.Context
import androidx.room.*
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.MetricsCurrentWeatherEntry
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.WeatherData
import java.lang.Class

@Database(
    entities = [WeatherData::class],
    version = 6
)
abstract class ForecastDatabase:RoomDatabase() {

    abstract fun currentWeatherDao():CurrentWeatherDao
    companion object{
        @Volatile
        private var instance :ForecastDatabase?=null
        fun getDatabase(context: Context):ForecastDatabase{
            if(instance ==null){
                synchronized(this){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }


        private fun buildDatabase(context:Context):ForecastDatabase{
            return  Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "forecast_DB").fallbackToDestructiveMigration().build()
        }



    }

}