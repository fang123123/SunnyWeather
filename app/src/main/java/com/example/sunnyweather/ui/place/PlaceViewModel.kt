package com.example.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place

/**
 * @author: Fangjie
 * @Date: Created in 22:55 2020/6/30
 * @version:
 * @Description:
 */
class PlaceViewModel : ViewModel() {
    //中间类，专门用来接收外界调用
    private val searchLiveData = MutableLiveData<String>()

    /**
     * 用于缓存界面数据
     */
    val placeList = ArrayList<Place>()

    /**
     * 网络请求返回的具体地址信息
     * @Type LiveData<Result<List<Place>>>
     */
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    /**
     * 查询地址信息接口
     * @param query String 要查询的地址
     */
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    /**
     * 保存地址
     * @param place Place
     */
    fun savePlace(place: Place) = Repository.savePlace(place)

    /**
     * 获取地址
     * @return Place
     */
    fun getSavedPlace() = Repository.getSavedPlace()

    /**
     * 地址是否保存
     * @return Boolean
     */
    fun isPlaceSaved() = Repository.isPlaceSaved()
}