package com.example.sunnyweather.ui.weather

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now_weather.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //让Activity的布局显示到状态栏上面
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        //将状态栏设置为透明
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_weather)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather) {
        val realtime = weather.realtime
        val daily = weather.daily
        //填充now_weather.xml布局
        //城市名
        placeName.text = viewModel.placeName
        //气温
        val currentTempText = "${realtime.temperature.toInt()} °C"
        currentTemp.text = currentTempText
        //天气
        currentSky.text = getSky(realtime.skycon).info
        nowWeatherLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //空气指数
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text

        //填充forecast.xml布局
        forecastLayout.removeAllViews()
        //返回天气预报的天数
        val days = daily.skycon.size
        for (i in 0 until days) {
            //填充forecast_item.xml布局，然后添加到forecast.xml布局

            //包含天气和时间信息
            val skycon = daily.skycon[i]
            //转换后的天气信息
            val sky = getSky(skycon.value)
            //包含当前最高温和最低温
            val temperature = daily.temperature[i]
            val temperatureText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()}"

            //动态构建forecast_item.xml布局
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)

            //赋值
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            skyIcon.setBackgroundResource(sky.icon)
            skyInfo.text = sky.info
            temperatureInfo.text = temperatureText

            forecastLayout.addView(view)
        }

        //填充life_index.xml布局
        //生活指数只取当前数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        //将界面显示
        weatherLayout.visibility = View.VISIBLE
    }
}