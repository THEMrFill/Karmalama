package com.karmarama.philip.arnold.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CityList(
    @PrimaryKey
    var id: String = "1",
    var cityList: RealmList<City>
): RealmObject() {
    constructor(): this("1", RealmList())
}