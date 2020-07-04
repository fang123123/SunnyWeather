package com.example.sunnyweather.logic


import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers


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
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            //从网络数据源获取数据
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        //将结果发射出去，类似调用LiveData对象的setValue()方法通知数据变化
        emit(result as Result<List<Place>>)
    }
}