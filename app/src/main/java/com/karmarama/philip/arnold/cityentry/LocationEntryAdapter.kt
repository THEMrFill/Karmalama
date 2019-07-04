package com.karmarama.philip.arnold.cityentry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import kotlinx.android.synthetic.main.recycler_location_entry.view.*

class LocationEntryAdapter(
    val cityList: ArrayList<CityLookup>,
    val mainActivityInterface: MainActivityInterface
        ): RecyclerView.Adapter<LocationEntryAdapter.LocationEntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationEntryViewHolder {
        return LocationEntryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_location_entry, parent, false), mainActivityInterface)
    }

    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: LocationEntryViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    class LocationEntryViewHolder(itemView: View, val mainActivityInterface: MainActivityInterface) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CityLookup) = with(itemView) {
            cityName.text = String.format("%s, %s", item.Name, item.Country)
            setOnClickListener { _ -> LinkTo(item) }
        }

        fun LinkTo(item: CityLookup) {
            mainActivityInterface.AddLocation(item)
        }
    }
}