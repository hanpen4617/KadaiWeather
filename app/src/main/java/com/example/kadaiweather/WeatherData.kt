package com.example.kadaiweather

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalDate

open class weatherData(@PrimaryKey
    var id: Long = 0,
    var dt: String = "",
    var weather: String = "",
    var temp: String = ""
    ): RealmObject()