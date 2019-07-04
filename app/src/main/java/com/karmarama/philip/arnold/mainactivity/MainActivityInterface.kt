package com.karmarama.philip.arnold.mainactivity

import androidx.fragment.app.Fragment
import com.karmarama.philip.arnold.model.CityLookup

interface MainActivityInterface {
    fun PushToFragment(fragment: Fragment, addToStack: Boolean = false)
    fun AddLocation(item: CityLookup)
    fun GoToCity(item: CityLookup, addToStack: Boolean = false)
    fun GetUnitsMetric(): Boolean
    fun SetUnitsMetric(isMetric: Boolean)
    fun SetToolbarTitle(title: String)
}