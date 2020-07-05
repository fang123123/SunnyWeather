package com.example.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Location

/**
 * @author: Fangjie
 * @Date: Created in 9:07 2020/7/5
 * @version:
 * @Description:
 */
class WeatherViewModel : ViewModel() {
    //接受外界调用
    private val searchLiveData = MutableLiveData<Location>()

    //缓存界面数据
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    /**
     *
     * @Type LiveData<Result<Weather>!>
     */
    val weatherLiveData = Transformations.switchMap(searchLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    /**
     * 更新天气信息
     * @param lng String 经度
     * @param lat String 纬度
     */
    fun refreshWeather(lng: String, lat: String) {
        searchLiveData.value = Location(lng, lat)
    }
}