package com.example.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author: Fangjie
 * @Date: Created in 22:28 2020/6/30
 * @version:
 * @Description: 网络数据源
 */
object SunnyWeatherNetwork {
    //构建地址请求服务
    private val placeService = ServiceCreator.create<PlaceService>()

    //构建天气请求服务
    private val weatherService = ServiceCreator.create<WeatherService>()

    /**
     * 获取地址的具体地址信息
     * @param query String 要查询的地址
     * @return PlaceResponse 具体地址信息
     */
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    /**
     * 获取地区实时天气
     * @param lng String 经度
     * @param lat String 纬度
     * @return RealtimeResponse
     */
    suspend fun searchRealtimeWeather(lng: String, lat: String) =
        weatherService.searchRealtimeWeather(lng, lat).await()

    /**
     * 获取地区未来几天天气
     * @param lng String 经度
     * @param lat String 纬度
     * @return DailyResponse
     */
    suspend fun searchDailyWeather(lng: String, lat: String) =
        weatherService.searchDailyWeather(lng, lat).await()

    //使用suspendCoroutine函数简化回调方式
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                        continuation.resume(body)
                    else
                        continuation.resumeWithException(RuntimeException("response body is null"))
                }

            })
        }
    }
}