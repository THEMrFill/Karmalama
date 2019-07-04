package com.karmarama.philip.arnold.cityentry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import com.karmarama.philip.arnold.retrofit.RetrofitFactory
import com.karmarama.philip.arnold.retrofit.RetrofitLocationService
import com.karmarama.philip.arnold.retrofit.citydata.GeoLookup
import kotlinx.android.synthetic.main.fragment_location_entry.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import kotlin.collections.ArrayList
import com.karmarama.philip.arnold.R.*
import java.util.*

class LocationEntryFragment(val mainActivityInterface: MainActivityInterface): Fragment() {
    val TAG = LocationEntryFragment::class.java.simpleName

    companion object {
        fun newInstance(mainActivityInterface: MainActivityInterface): LocationEntryFragment {
            return LocationEntryFragment(mainActivityInterface)
        }
    }

    var cityName: TextInputEditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.fragment_location_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(com.karmarama.philip.arnold.R.id.search).setOnClickListener({ _ ->
            SearchForCities()
        })
        cityName = view.findViewById(com.karmarama.philip.arnold.R.id.cityEntry)
        cityName!!.addTextChangedListener(object: TextWatcher {
            var timer = Timer()
            val DELAY: Long = 1000

            override fun afterTextChanged(s: Editable) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            SearchForCities()
                        }
                    },
                    DELAY
                )
            }

            override fun beforeTextChanged(s: CharSequence,
                                           start: Int,
                                           count: Int,
                                           after: Int) {

            }

            override fun onTextChanged(s: CharSequence,
                                       start: Int,
                                       before: Int,
                                       count: Int) {
            }
        })
        recycler.layoutManager = LinearLayoutManager(context)
        mainActivityInterface.SetToolbarTitle(getString(string.add_a_city))
    }

    fun SearchForCities() {
        val service = RetrofitFactory.cityRetrofit.create(RetrofitLocationService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val call = service.getCities(cityName!!.text.toString())
            withContext(Dispatchers.Main) {
                HandleCityLookup(call)
            }
        }
    }

    private fun HandleCityLookup(call: Response<GeoLookup>) {
        try {
            val cities = ArrayList<CityLookup>()
            if (call.isSuccessful) {
                for (result in call.body()!!.results) {
                    val output = result.formatted_address
                    val parts = output.split(",")
                    val country = parts.last()
                    val city = parts[parts.size - 2]
                    if (city.length > 0 && country.length > 0) {
                        cities.add(
                            CityLookup(
                                city,
                                country,
                                result.geometry.location.lat.toFloat(),
                                result.geometry.location.lng.toFloat()
                            )
                        )
                    }
                }
                val adapter = LocationEntryAdapter(cities, mainActivityInterface)
                recycler.adapter = adapter
            }
        } catch (e: HttpException) {
        } catch (e: Throwable) {
        }
    }
}