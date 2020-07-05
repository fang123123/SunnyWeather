package com.example.sunnyweather.logic.model

/**
 * @author: Fangjie
 * @Date: Created in 18:24 2020/7/4
 * @version:
 * @Description: 天气信息
 */ 
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)