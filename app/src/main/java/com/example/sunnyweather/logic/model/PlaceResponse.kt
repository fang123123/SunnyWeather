package com.example.sunnyweather.logic.model

import android.location.Location
import com.google.gson.annotations.SerializedName

/**
 * @author: Fangjie
 * @Date: Created in 22:05 2020/6/30
 * @version:
 * @Description: 地址实体类
 */
/**
 * 查询地址信息结果
 * @property status String
 * @property places List<Place>
 * @constructor
 */
data class PlaceResponse(val status: String,val places:List<Place>)

/**
 * 地址信息
 * @property name String
 * @property location Location
 * @property address String
 * @constructor
 */
data class Place(val name:String, val location: Location, @SerializedName("formatted_address") val address:String)

/**
 * 地址坐标
 * @property lng String longitude 经度
 * @property lat String latitude 纬度
 * @constructor
 */
data class Location(val lng: String, val lat: String)