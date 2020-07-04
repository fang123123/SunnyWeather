package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: Fangjie
 * @Date: Created in 22:24 2020/6/30
 * @version:
 * @Description: 网络服务创建者
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //使用泛型实化来简化函数调用
    inline fun <reified T> create(): T = create(T::class.java)
}