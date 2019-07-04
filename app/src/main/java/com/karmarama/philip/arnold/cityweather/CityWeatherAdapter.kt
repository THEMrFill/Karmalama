package com.karmarama.philip.arnold.cityweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import com.karmarama.philip.arnold.model.Weather
import com.karmarama.philip.arnold.utils.Formatter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_weather.view.*

class CityWeatherAdapter(
    val weather: ArrayList<Weather>,
    val mainActivityInterface: MainActivityInterface
): RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        return CityWeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_weather, parent, false), mainActivityInterface)
    }

    companion object {
        val baseImageUrl = "http://openweathermap.org/img/wn/%s@2x.png"
    }

    override fun getItemCount() = weather.size - 1

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        holder.bind(weather[position + 1])
    }

    class CityWeatherViewHolder(view: View, val mainActivityInterface: MainActivityInterface): RecyclerView.ViewHolder(view) {
        fun bind(item: Weather) = with(itemView) {
            dayDay.text = Formatter.DayFormatter(item.timestamp)
            dayTemp.text = Formatter.TempFormatter(item.temp, mainActivityInterface.GetUnitsMetric())

            Picasso
                .get()
                .load(String.format(baseImageUrl, item.icon))
                .into(dayIcon)
        }
    }
}