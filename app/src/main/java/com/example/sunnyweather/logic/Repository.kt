package com.example.sunnyweather.logic


import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


/**
 * @author: Fangjie
 * @Date: Created in 22:46 2020/6/30
 * @version:
 * @Description: 仓库层
 */
object Repository {
    /**
     * 获取地址的具体地址信息
     * @param query String 要查询的地址
     * @return LiveData<Result<List<Place>>>
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        //从网络数据源获取数据
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
     * 获取地址的天气信息
     * @param lng String 经度
     * @param lat String 纬度
     * @return LiveData<Result<Weather>>
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { SunnyWeatherNetwork.searchRealtimeWeather(lng, lat) }
            val deferredDaily = async { SunnyWeatherNetwork.searchDailyWeather(lng, lat) }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}"
                                + "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    /**
     * 封装一个统一的入口函数
     * 在函数类型前面使用suspend关键字，表明Lambda表达式也拥有挂起函数上下文
     * @param context CoroutineContext 协程上下文
     * @param block SuspendFunction0<Result<T>> 挂起函数
     * @return LiveData<Result<T>>
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: java.lang.Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    /**
     * 保存地址
     * @param place Place
     */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    /**
     * 获取地址
     * @return Place
     */
    fun getSavedPlace() = PlaceDao.getSavedPlace()

    /**
     * 地址是否保存
     * @return Boolean
     */
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}