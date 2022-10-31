package com.example.kadaiweather

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WeatherGetter {
    private val key = "9c5b8afab877ebe11f361113ac477602"
    fun dailyGet(lat: Double, lon:Double):Pair<List<String>,List<String>>{
        val dailyWeather = mutableListOf<String>()
        val dailyTemp = mutableListOf<String>()
        val API_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&lang=ja&APPID=$key"
        val url = URL(API_URL)
        //APIから情報を取得する.
        val br = BufferedReader(InputStreamReader(url.openStream()))
        //取得した情報を文字列に変換
        val str = br.readText()
        //json形式のデータとして識別
        val json = JSONObject(str)
        //日別の配列を取得
        val daily = json.getJSONArray("daily")

        //7日分の天気を取得
        for (i in 0..6) {
            val firstObject = daily.getJSONObject(i)
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            //天気を取得
            val descriptionText = weather.getString("description")
            //リストに天気を追加
            dailyWeather.add(descriptionText)
        }
        println(dailyWeather)

        for (i in 0..6) {
            val firstObject = daily.getJSONObject(i)
            val temp = firstObject.getJSONObject("temp")
            //気温を取得
            val day = temp.getString("day")
            dailyTemp.add("$day℃")
        }
        println(dailyTemp)

        return dailyWeather to dailyTemp
    }


    fun houryGet(lat: Double,lon: Double):Pair<List<String>,List<String>>{
        val houryWeather = mutableListOf<String>()
        val houryTemp = mutableListOf<String>()

        val API_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&lang=ja&APPID=$key"
        val url = URL(API_URL)
        //APIから情報を取得する.
        val br = BufferedReader(InputStreamReader(url.openStream()))
        //取得した情報を文字列に変換
        val str = br.readText()
        //json形式のデータとして識別
        val json = JSONObject(str)
        //時間別の配列を取得
        val hourly = json.getJSONArray("hourly")
        //12時間分の天気予報を取得
        for (i in 0..11) {
            //ここに時間を
            val firstObject = hourly.getJSONObject(i)
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            //天気を取得
            val descriptionText = weather.getString("description")
            //リストに天気を追加
            houryWeather.add(descriptionText)
        }
        println(houryWeather)
        //時間別の配列を取得
        for (i in 0..11) {
            val firstObject = hourly.getJSONObject(i)
            val temp = firstObject.getString("temp")
            houryTemp.add("$temp℃")
        }
        println(houryTemp)

        return houryWeather to houryTemp
    }
}