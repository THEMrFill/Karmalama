package com.karmarama.philip.arnold

import android.app.Application
import io.realm.Realm

class KarmaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}