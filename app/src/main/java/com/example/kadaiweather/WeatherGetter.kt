package com.example.kadaiweather

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.*
import java.time.format.DateTimeFormatter

class WeatherGetter {
    private val key = "9c5b8afab877ebe11f361113ac477602"
    fun dailyGet(lat: Double, lon:Double):Triple<List<String>,List<String>,List<String>>{
        val dailyWeather = mutableListOf<String>()
        val dailyTemp = mutableListOf<String>()
        val week = mutableListOf<String>()
        val API_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&lang=ja&APPID=$key"
        val url = URL(API_URL)
        //APIから情報を取得する.
        val br = BufferedReader(InputStreamReader(url.openStream()))
        //取得した情報を文字列に変換
        val str = br.readText()       //json形式のデータとして識別
        val json = JSONObject(str)
        //日別の配列を取得
        val daily = json.getJSONArray("daily")

        //7日分の天気を取得
        for (i in 0..6) {
            val firstObject = daily.getJSONObject(i)

            //Jsonファイルから要素抽出↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            val temp = firstObject.getJSONObject("temp")
            val dt = firstObject.getLong("dt")

            val descriptionText = weather.getString("description")
            val tempText = temp.getString("day")
            //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
            //タイムスタンプ変換
            val instant = Instant.ofEpochSecond(dt)
            val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            //各リストに格納
            dailyWeather.add(descriptionText)
            dailyTemp.add("$tempText℃")
            week.add(zone.toString())
        }
        val triple = Triple(dailyWeather,dailyTemp,week)
        return triple
    }


    fun houryGet(lat: Double,lon: Double):Triple<List<String>,List<String>,List<LocalTime>>{
        val houryWeather = mutableListOf<String>()
        val houryTemp = mutableListOf<String>()
        val houry = mutableListOf<LocalTime>()

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
            val firstObject = hourly.getJSONObject(i)
            val weather = firstObject.getJSONArray("weather").getJSONObject(0)
            val descriptionText = weather.getString("description")
            val temp = firstObject.getString("temp")
            val dt = firstObject.getLong("dt")
            val instant = Instant.ofEpochSecond(dt)
            val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())

            //リストに天気を追加
            houryWeather.add(descriptionText)
            houryTemp.add("$temp℃")
            houry.add(zone.toLocalTime())
        }
        val triple = Triple(houryWeather,houryTemp,houry)
        return triple
    }
}