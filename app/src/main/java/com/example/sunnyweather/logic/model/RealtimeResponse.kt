package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Fangjie
 * @Date: Created in 18:05 2020/7/4
 * @version:
 * @Description: 实时天气
 */
data class RealtimeResponse(val status: String, val result: Result) {
    //将数据模型全部定义在类中，防止和其他数据模型同名冲突

    data class Result(val realtime: Realtime)

    data class Realtime(
        val temperature: Float,
        val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}