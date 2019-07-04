package com.karmarama.philip.arnold.mainactivity

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment
import com.karmarama.philip.arnold.R
import com.karmarama.philip.arnold.cityentry.LocationEntryFragment
import com.karmarama.philip.arnold.model.CityLookup
import com.karmarama.philip.arnold.citylist.LocationListFragment
import com.karmarama.philip.arnold.cityweather.CityWeatherFragment
import com.karmarama.philip.arnold.model.City
import com.karmarama.philip.arnold.model.CityList
import com.karmarama.philip.arnold.settings.SettingsFragment
import io.realm.Realm

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainActivityInterface {
    var cityList: CityList? = null
    var realm: Realm? = null
    var fab: FloatingActionButton? = null

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "Karmalama"
    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        LoadRecords()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        fab = findViewById(R.id.fab)
        fab!!.setOnClickListener { _ ->
            PushToLocationEntry()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        PushToLocationList()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                PushToSettings()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_list -> {
                PushToLocationList()
            }
            R.id.nav_entry -> {
                PushToLocationEntry()
            }
            R.id.nav_settings -> {
                PushToSettings()
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun PushToFragment(fragment: Fragment, addToStack: Boolean) {
        FabDisplay(if (fragment is LocationEntryFragment) false else true)
        ClearBackStack()
        val transaction = supportFragmentManager.beginTransaction()
        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }

    override fun AddLocation(item: CityLookup) {
        val city = City(item.Name, item.Country, item.Latitude, item.Longitude)
        cityList!!.cityList.add(city)
        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(cityList)
        realm!!.commitTransaction()
        PushToLocationList()
    }

    override fun GoToCity(item: CityLookup, addToStack: Boolean) {
        val fragment = CityWeatherFragment.newInstance(item, this)
        PushToFragment(fragment, addToStack)
    }

    override fun GetUnitsMetric(): Boolean {
        val isMetric = sharedPref!!.getBoolean(PREF_NAME, true)
        return isMetric
    }
    override fun SetUnitsMetric(isMetric: Boolean) {
        val editor = sharedPref!!.edit()
        editor.putBoolean(PREF_NAME, isMetric)
        editor.apply()
    }

    override fun SetToolbarTitle(title: String) {
        supportActionBar!!.title = title
    }

    fun FabDisplay(toShow: Boolean?) {
        if (toShow!!) {
            fab!!.show()
        } else {
            fab!!.hide()
        }
    }

    fun PushToSettings() {
        val fragment = SettingsFragment.newInstance(this)
        PushToFragment(fragment)
    }

    fun PushToLocationEntry() {
        val fragment = LocationEntryFragment.newInstance(this)
        PushToFragment(fragment)
    }

    fun PushToLocationList() {
        val cities = ArrayList<CityLookup>()
        cityList!!.cityList.forEach {
            cities.add(CityLookup(it.Name, it.Country, it.Latitude, it.Longitude))
        }
        PushToFragment(LocationListFragment.newInstance(this, cities))
    }

    fun ClearBackStack() {
        val fm = getSupportFragmentManager()
        for (i in 0 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
    }

    fun LoadRecords() {
        realm = Realm.getDefaultInstance()
        try {
            val realm2 = realm
            cityList = realm2!!.where(CityList::class.java).findFirst()
            cityList = realm2.copyFromRealm(cityList)
        } catch (e: Exception) {
        }

        if (cityList == null) {
            cityList = CityList()
        }
    }
}
