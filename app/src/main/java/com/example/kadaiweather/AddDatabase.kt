package com.example.kadaiweather

import android.app.Application
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.time.LocalDate


class AddDatabase: Application() {
     fun addDB(dailyWeather:List<String>,dailyTemp:List<String>,week:List<String>){
        //データベースに保存するだけ
        val realm = Realm.getDefaultInstance()
         realm.executeTransaction{
             val maxId = realm.where<weatherData>().max("id")
             val nextId = (maxId?.toLong() ?: 0L) + 1L
             val data = realm.createObject<weatherData>(nextId)
             //for分でぶん回す
             for(i in 0..6){
                 data.weather = dailyWeather[i]
                 data.temp = dailyTemp[i]
                 data.dt = week[i]
             }
             println("ok")
         }




    }
}