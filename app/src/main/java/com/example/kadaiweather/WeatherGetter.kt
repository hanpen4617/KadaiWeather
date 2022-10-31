package com.example.kadaiweather

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class WeatherGetter {
    private val key = "9c5b8afab877ebe11f361113ac477602"
    fun weatherGet(lat: Double,lon: Double):List<String>{
        val weatherList = mutableListOf<String>()
        val API_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&lang=ja&APPID=$key"
        val url = URL(API_URL)
        //APIから情報を取得する.
        val br = BufferedReader(InputStreamReader(url.openStream()))
        // 所得した情報を文字列化
        val str = br.readText()
        //json形式のデータとして識別
        val json = JSONObject(str)

        // hourlyの配列を取得
        val hourly = json.getJSONArray("hourly")
        // 十時間分の天気予報を取得
        for (i in 0..11) {
            val firstObject = hourly.getJSONObject(i)
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            // unixtime形式で保持されている時刻を取得
            // 天気を取得
            val descriptionText = weather.getString("description")
            weatherList.add(descriptionText)
        }
        println(weatherList)


        // dailyの配列を取得
        val daily = json.getJSONArray("daily")
        // 十時間分の天気予報を取得
        for (i in 0..6) {
            val firstObject = daily.getJSONObject(i)
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            // unixtime形式で保持されている時刻を取得
            // 天気を取得
            val descriptionText = weather.getString("description")
            weatherList.add(descriptionText)
        }
        println(weatherList)
        return weatherList
    }
   fun tempGet(lat: Double, lon:Double):List<String>{
       val tempList = mutableListOf<String>()
       val API_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&lang=ja&APPID=$key"
       val url = URL(API_URL)
       //APIから情報を取得する.
       val br = BufferedReader(InputStreamReader(url.openStream()))
       // 所得した情報を文字列化
       val str = br.readText()
       //json形式のデータとして識別
       val json = JSONObject(str)

       // hourlyの配列を取得
       val hourly = json.getJSONArray("hourly")
       for (i in 0..11) {
           val firstObject = hourly.getJSONObject(i)
           val temp = firstObject.getString("temp")
           tempList.add(temp+"℃")
       }
       println(tempList)
       // hourlyの配列を取得
       val daily = json.getJSONArray("daily")
       for (i in 0..6) {
           val firstObject = daily.getJSONObject(i)

           // unixtime形式で保持されている時刻を取得
           // 天気を取得
           val day = temp.getString("day")
           tempList.add(day)
       }
       println(tempList)
        return tempList
    }
}