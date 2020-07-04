package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: Fangjie
 * @Date: Created in 22:20 2020/6/30
 * @version:
 * @Description: 地址请求服务
 */
interface PlaceService {
    /**
     * 发送地址信息请求
     * @param query String 要查询的地址
     * @return Call<PlaceResponse> 具体地址信息
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
}