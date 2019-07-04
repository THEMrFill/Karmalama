package com.karmarama.philip.arnold.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.model.City
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import kotlinx.android.synthetic.main.recycler_location_entry.view.*

class LocationListAdapter(
    val cityList: ArrayList<CityLookup>,
    val mainActivityInterface: MainActivityInterface
        ): RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    var cities: ArrayList<City> = ArrayList()

    fun loadCities(newCities: ArrayList<City>) {
        cities = newCities
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_location_entry, parent, false), mainActivityInterface)
    }

    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    class LocationViewHolder(view: View, val mainActivityInterface: MainActivityInterface): RecyclerView.ViewHolder(view) {
        fun bind(item: CityLookup) = with(itemView) {
            cityName.text = String.format("%s, %s", item.Name, item.Country).trim()
            setOnClickListener { _ -> LinkTo(item) }
        }

        fun LinkTo(item: CityLookup) {
            mainActivityInterface.GoToCity(item, true)
        }
    }
}