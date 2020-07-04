package com.example.sunnyweather

import android.app.Application
import android.content.Context

/**
 * @author: Fangjie
 * @Date: Created in 19:00 2020/6/30
 * @version:
 * @Description:
 */ 
class SunnyWeatherApplication : Application(){
    companion object{
        //彩云天气令牌
        const val TOKEN = "YTSnb3Wub1H4TkWe"

        //全局Context
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext;
    }
}