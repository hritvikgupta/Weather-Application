package com.example.weatherapplicationusingretrofitapi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplicationusingretrofitapi.adapters.futureParams
import com.example.weatherapplicationusingretrofitapi.adapters.futureWeatherAdapter
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.Current
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.DbWorkerThread
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.ForecastDatabase
import com.example.weatherapplicationusingretrofitapi.futureWeatherFiles.unitlocalized.WeatherData
import com.example.weatherapplicationusingretrofitapi.network.futureWeatherInterface
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var currentCityName:TextView
    private lateinit var editTextField:EditText
    private lateinit var searchView: ImageView
    private lateinit var currentTemperature:TextView
    private lateinit var currentWeatherIcon: ImageView
    private lateinit var weatherCondition:TextView
    private lateinit var futureWeatherConditionText:TextView
    private lateinit var futureRecyclerView:RecyclerView

    private lateinit var futureWeatherAdapter:futureWeatherAdapter
    private lateinit var futureWeatherList : ArrayList<futureParams>
    private var place: String? =null
    private lateinit var bottom_nav:BottomNavigationView
    private lateinit var long:String
    private lateinit var test:String
    private lateinit var wDataBase:Current

    private lateinit var mDbWorkerThread: DbWorkerThread
    private var forecastDb:ForecastDatabase?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherCondition = findViewById(R.id.weatherCondition)
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.Toolbar)
        toolbar.title = "Weather Forecast"
        setSupportActionBar(toolbar)
        bottom_nav = findViewById(R.id.bottom_nav)
        bottom_nav.setSelectedItemId(R.id.currentWeatherFragment);

        futureWeatherConditionText = findViewById(R.id.futureWeatherConditionText)

        bottom_nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settingsFragment ->{
                    val intent = Intent(this, setting::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
        editTextField = findViewById(R.id.EditTextField)
        currentCityName = findViewById(R.id.currentCityName)
        currentTemperature = findViewById(R.id.currentTemperature)


        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        forecastDb = ForecastDatabase.getDatabase(this)
        val futureCityInterface = futureWeatherInterface()


        searchView = findViewById(R.id.searchView)
        searchView.setOnClickListener{
            place = editTextField.text.toString()
            currentCityName.text = place
            GlobalScope.launch(Dispatchers.Main) {
                val wR = futureCityInterface.getLongLat(place!!,"5").await()
                currentTemperature.text = wR.current.tempC.toString() + "°C"
                Picasso
                    .get()
                    .load("http:"+wR.current.condition.icon)
                    .into(currentWeatherIcon);
                val conditionPlusCity= wR.current.condition.text
                weatherCondition.text = conditionPlusCity
                futureWeatherList = ArrayList()
                futureWeatherList.add(futureParams(wR.forecast.forecastday[0].day.avgtempC.toString() + " " + "°C",wR.forecast.forecastday[0].day.condition.icon,wR.forecast.forecastday[0].day.condition.text.toString()))
                futureWeatherList.add(futureParams(wR.forecast.forecastday[1].day.avgtempC.toString() + " " + "°C",wR.forecast.forecastday[1].day.condition.icon,wR.forecast.forecastday[1].day.condition.text.toString()))
                futureWeatherList.add(futureParams(wR.forecast.forecastday[2].day.avgtempC.toString() + " " + "°C",wR.forecast.forecastday[2].day.condition.icon,wR.forecast.forecastday[2].day.condition.text.toString()))
                futureWeatherList.add(futureParams(wR.forecast.forecastday[3].day.avgtempC.toString() + " " + "°C",wR.forecast.forecastday[3].day.condition.icon,wR.forecast.forecastday[3].day.condition.text.toString()))
                futureWeatherList.add(futureParams(wR.forecast.forecastday[4].day.avgtempC.toString() + " " + "°C",wR.forecast.forecastday[4].day.condition.icon,wR.forecast.forecastday[4].day.condition.text.toString()))

                futureRecyclerView = findViewById(R.id.futureRecyclerView)
                futureWeatherAdapter = futureWeatherAdapter(applicationContext, futureWeatherList)
                futureRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                futureRecyclerView.adapter = futureWeatherAdapter


            }
        }


        if (checkForInternet(this)) {

            futureWeatherList = ArrayList()
            currentCityName.text = "Riverside"
            GlobalScope.launch(Dispatchers.Main) {
                val wR = futureCityInterface.getLongLat("Riverside", "5").await()
                //val apiService = weatherInterface()
                //val currentResponse = apiService.getCurrentWeatherAsync("Riverside", unit = "m", "4").await()
                test = wR.current.tempC.toString()
                currentTemperature.text = wR.current.tempC.toString() + "" + "°C"
                //Toast.makeText(applicationContext, test, Toast.LENGTH_SHORT).show()

                weatherCondition.text = wR.current.condition.text.toString()
                Picasso
                    .get()
                    .load("http:" + wR.current.condition.icon)
                    .into(currentWeatherIcon);
                futureWeatherList.add(
                    futureParams(
                        wR.forecast.forecastday[0].day.avgtempC.toString() + " " + "°C",
                        wR.forecast.forecastday[0].day.condition.icon,
                        wR.forecast.forecastday[0].day.condition.text.toString()
                    )
                )
                futureWeatherList.add(
                    futureParams(
                        wR.forecast.forecastday[1].day.avgtempC.toString() + " " + "°C",
                        wR.forecast.forecastday[1].day.condition.icon,
                        wR.forecast.forecastday[1].day.condition.text.toString()
                    )
                )
                futureWeatherList.add(
                    futureParams(
                        wR.forecast.forecastday[2].day.avgtempC.toString() + " " + "°C",
                        wR.forecast.forecastday[2].day.condition.icon,
                        wR.forecast.forecastday[2].day.condition.text.toString()
                    )
                )
                futureWeatherList.add(
                    futureParams(
                        wR.forecast.forecastday[3].day.avgtempC.toString() + " " + "°C",
                        wR.forecast.forecastday[3].day.condition.icon,
                        wR.forecast.forecastday[3].day.condition.text.toString()
                    )
                )
                futureWeatherList.add(
                    futureParams(
                        wR.forecast.forecastday[4].day.avgtempC.toString() + " " + "°C",
                        wR.forecast.forecastday[4].day.condition.icon,
                        wR.forecast.forecastday[4].day.condition.text.toString()
                    )
                )

                futureRecyclerView = findViewById(R.id.futureRecyclerView)
                futureWeatherAdapter = futureWeatherAdapter(applicationContext, futureWeatherList)
                futureRecyclerView.layoutManager =
                    LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                futureRecyclerView.adapter = futureWeatherAdapter

                val weatherData = WeatherData()
                weatherData.tempC = wR.current.tempC
                weatherData.tempInF = wR.current.tempF
                weatherData.condition_text = wR.current.condition.text.toString()
                weatherData.condition_icon = wR.current.condition.icon

                insertWeatherData(weatherData)
            }
            //Toast.makeText(this, test, Toast.LENGTH_SHORT).show()
        }

        else
        {
            futureWeatherConditionText.text = "!! please Turn on the Internet Connection"
            futureWeatherConditionText.showSoftInputOnFocus = false
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            fetchDataFromDb()
        }

    }

    private fun insertWeatherData(weatherData:WeatherData){
        val size = forecastDb?.currentWeatherDao()?.getWeatherMetric()?.value
        //Toast.makeText(this, ""+size, Toast.LENGTH_SHORT).show()
        val task = Runnable { forecastDb?.currentWeatherDao()?.insertWeather(weatherData) }
        mDbWorkerThread.postTask(task)
    }
    private fun updateWeatherData(weatherData: WeatherData){
        val task = Runnable { forecastDb?.currentWeatherDao()?.update(weatherData) }
        mDbWorkerThread.postTask(task)
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
    private fun bindWithUi(weatherData:WeatherData){
        currentTemperature.text = weatherData.tempC.toString() + " " + "°C"
        weatherCondition.text = weatherData.condition_text
        currentCityName.text = "Riverside"
        Picasso.get().load("http:" + weatherData.condition_icon).into(currentWeatherIcon)

    }

    private fun fetchDataFromDb(){
        val task = Runnable {

            val uiHandler = Handler(Looper.getMainLooper())
            uiHandler.post({
                forecastDb?.currentWeatherDao()?.getWeatherMetric()?.observe(
                    this, Observer {
                        val value = it[it.size-1]
                        bindWithUi(value)
                    }
                )

            })

        }
        mDbWorkerThread.postTask(task)
    }



}

