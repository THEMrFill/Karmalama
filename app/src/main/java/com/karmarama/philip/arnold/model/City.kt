package com.karmarama.philip.arnold.model

import io.realm.RealmObject

open class City(
    var Name: String?,
    var Country: String?,
    var Latitude: Float?,
    var Longitude: Float?
): RealmObject() {
    constructor(): this(null, null, 0f, 0f)
}