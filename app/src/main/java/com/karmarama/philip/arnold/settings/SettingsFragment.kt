package com.karmarama.philip.arnold.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.mainactivity.MainActivityInterface
import com.karmarama.philip.arnold.retrofit.WeatherDefaults

class SettingsFragment(val mainActivityInterface: MainActivityInterface): Fragment() {
    var units: String = WeatherDefaults.UnitsMetric
    lateinit var switchLabel: TextView
    lateinit var switchUnits: Switch

    companion object {
        fun newInstance(mainActivityInterface: MainActivityInterface): SettingsFragment {
            return SettingsFragment(mainActivityInterface)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        switchLabel = view.findViewById(R.id.switchLabel)
        switchUnits = view.findViewById(R.id.unitsSwitch)
        switchUnits.setChecked(!mainActivityInterface.GetUnitsMetric())
        switchUnits.setOnClickListener({
            if (switchUnits.isChecked) {
                units = WeatherDefaults.UnitsImperial
            } else {
                units = WeatherDefaults.UnitsMetric
            }
            ChangeUnits()
        })
        mainActivityInterface.SetToolbarTitle(getString(R.string.settings))
        return view
    }

    fun ChangeUnits() {
        mainActivityInterface.SetUnitsMetric(!switchUnits.isChecked)
    }
}