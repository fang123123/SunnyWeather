package com.example.sunnyweather.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.sunnyweather.SunnyWeatherApplication

/**
 * @author: Fangjie
 * @Date: Created in 15:23 2020/7/5
 * @version:
 * @Description:
 */
object Utils {

    /**
     * 封装一个SharedPreferences对象
     * @return (android.content.SharedPreferences..android.content.SharedPreferences?)
     */
    val sharedPreferences: SharedPreferences by lazy {
        SunnyWeatherApplication.context.getSharedPreferences(
            "sunny_weather",
            Context.MODE_PRIVATE
        )
    }

    inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
        val intent = Intent(context, T::class.java)
        intent.block()
        context.startActivity(intent)
    }
}
