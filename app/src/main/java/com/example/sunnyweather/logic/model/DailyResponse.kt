package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author: Fangjie
 * @Date: Created in 18:17 2020/7/4
 * @version:
 * @Description: 未来几天天气
 */

data class DailyResponse(val status: String, val result: Result) {
    //定义List集合来获取Json中的数组数据

    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}