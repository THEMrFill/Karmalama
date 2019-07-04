package com.karmarama.philip.arnold.model

class Weather(
    var timestamp: String,
    var main: String,
    var description: String,
    var icon: String,
    var temp: Double,
    var temp_min: Double,
    var temp_max: Double,
    var wind_speed: Double,
    var wind_direction: Double
) {
    constructor(): this("", "", "", "", 0.0, 0.0, 0.0, 0.0, 0.0)
}