package com.karmarama.philip.arnold.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface

class LocationListFragment(val mainActivityInterface: MainActivityInterface, val cities: ArrayList<CityLookup>): Fragment() {
    companion object {
        fun newInstance(mainActivityInterface: MainActivityInterface, cities: ArrayList<CityLookup>): LocationListFragment {
            return LocationListFragment(mainActivityInterface, cities)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location_list, container, false)
        if (cities.size == 0) {
            view.findViewById<AppCompatTextView>(R.id.addCitiesLabel).visibility = View.VISIBLE
            view.findViewById<AppCompatTextView>(R.id.title).visibility = View.GONE
        } else {
            view.findViewById<AppCompatTextView>(R.id.addCitiesLabel).visibility = View.GONE
            view.findViewById<AppCompatTextView>(R.id.title).visibility = View.VISIBLE

            DeDupeLocations()
            val adapter = LocationListAdapter(cities, mainActivityInterface)
            val recycler = view.findViewById<RecyclerView>(R.id.recycler)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context)
        }
        mainActivityInterface.SetToolbarTitle(getString(R.string.locations))
        return view
    }

    fun DeDupeLocations() {
        val newCities = ArrayList<CityLookup>()
        for (city in cities) {
            var addCity = true
            for (checkCity in newCities) {
                if (checkCity.Latitude == city.Latitude && checkCity.Longitude == city.Longitude) {
                    addCity = false
                    break
                }
            }
            if (addCity) {
                newCities.add(city)
            }
        }
        cities.clear()
        cities.addAll(newCities)
    }
}