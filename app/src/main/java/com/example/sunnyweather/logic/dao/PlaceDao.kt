package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.util.Utils
import com.google.gson.Gson

/**
 * @author: Fangjie
 * @Date: Created in 20:03 2020/7/5
 * @version:
 * @Description:
 */
object PlaceDao {
    /**
     * 存储地址的key
     */
    private const val placeKey = "place"

    /**
     * 使用SharedPreferences方式持久化地址
     * @param place Place
     */
    fun savePlace(place: Place) {
        Utils.sharedPreferences.edit() {
            putString(placeKey, Gson().toJson(place))
        }
    }

    /**
     * 从SharedPreferences中获取地址
     * @return Place
     */
    fun getSavedPlace(): Place {
        val placeJson = Utils.sharedPreferences.getString(placeKey, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    /**
     * 判断地址是否持久化
     * @return Boolean
     */
    fun isPlaceSaved() = Utils.sharedPreferences.contains(placeKey)

}