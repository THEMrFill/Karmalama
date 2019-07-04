package com.karmarama.philip.arnold.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.model.Weather
import com.karmarama.philip.arnold.retrofit.RetrofitFactory
import com.karmarama.philip.arnold.retrofit.RetrofitWeatherService
import com.karmarama.philip.arnold.retrofit.WeatherDefaults
import com.karmarama.philip.arnold.retrofit.weatherdata.WeatherLookup
import com.karmarama.philip.arnold.utils.Formatter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CityWeatherFragment(val city: CityLookup, val mainActivityInterface: MainActivityInterface): Fragment() {
    var units: String = WeatherDefaults.UnitsMetric
    lateinit var recycler: RecyclerView
    lateinit var todayDay: TextView
    lateinit var todayIcon: ImageView
    lateinit var todayTemp: TextView
    val weather: ArrayList<Weather> = ArrayList()
    val TAG = CityWeatherFragment::class.java.simpleName

    companion object {
        fun newInstance(city: CityLookup, mainActivityInterface: MainActivityInterface): CityWeatherFragment {
            return CityWeatherFragment(city, mainActivityInterface)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather_list, container, false)

        todayDay = view.findViewById(R.id.todayDay)
        todayIcon = view.findViewById(R.id.todayIcon)
        todayTemp = view.findViewById(R.id.todayTemp)
        recycler = view.findViewById(R.id.recycler)

        mainActivityInterface.SetToolbarTitle(String.format("%s, %s", city.Name, city.Country))

        LookupWeather()

        return view
    }

    fun LookupWeather() {
        val service = RetrofitFactory.weatherRetrofit.create(RetrofitWeatherService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val call = service.getWeather(city.Latitude!!, city.Longitude!!,
                if (mainActivityInterface.GetUnitsMetric()) {
                    WeatherDefaults.UnitsMetric
                } else {
                    WeatherDefaults.UnitsImperial
                }
            )
            withContext(Dispatchers.Main) {
                CalculateWeatherParts(call)
            }
        }
    }

    private fun CalculateWeatherParts(call: Response<WeatherLookup>) {
        if (call.isSuccessful) {
            weather.clear()
            var datePart = ""
            var timePart = ""
            val timesToCheck = arrayOf("12:00:00", "13:00:00", "14:00:00", "15:00:00")
            for (result in call.body()!!.list) {
                val parts = result.dt_txt.split(" ")
                if (datePart.length == 0) {
                    datePart = parts[0]
                }
                timePart = parts[1]
                if (weather.size == 0 || (timesToCheck.contains(timePart) && weather.size < 4)) {
                    datePart = parts[0]
                    val thisWeather = Weather(
                        result.dt_txt,
                        result.weather[0].main,
                        result.weather[0].description,
                        result.weather[0].icon,
                        result.main.temp,
                        result.main.temp_min,
                        result.main.temp_max,
                        result.wind.speed,
                        result.wind.deg
                    )
                    weather.add(thisWeather)
                }
            }
            ShowWeather()
        }
    }

    fun ShowWeather() {
        val item = weather.get(0)
        todayDay.text = Formatter.DayFormatter(item.timestamp, false)
        todayTemp.text = Formatter.TempFormatter(item.temp, mainActivityInterface.GetUnitsMetric())
        val newImage = String.format(CityWeatherAdapter.baseImageUrl, item.icon)
        Picasso
            .get()
            .load(newImage)
            .into(todayIcon)

        val adapter = CityWeatherAdapter(weather, mainActivityInterface)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}