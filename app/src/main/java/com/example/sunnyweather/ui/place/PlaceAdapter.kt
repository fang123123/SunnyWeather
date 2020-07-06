package com.example.sunnyweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.ui.weather.WeatherActivity
import com.example.sunnyweather.util.Utils
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * @author: Fangjie
 * @Date: Created in 23:21 2020/6/30
 * @version:
 * @Description: 由于ViewModel通常和Activity是一一对应，所以放在ui包下
 */
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    /**
     * 缓存item视图
     * @property placeName TextView
     * @property placeAddress TextView
     * @constructor
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            //保存选中的地址
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                //如果在WeatherActivity中，就关闭滑动菜单
                activity.drawerLayout.closeDrawers()
                activity.viewModel.placeName = place.name
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.refreshWeather()
            } else {
                //界面跳转
                Utils.startActivity<WeatherActivity>(parent.context) {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                //结束当前fragment
                activity?.finish()
            }
            //当前面逻辑处理成功再保存，如果出错后，保存了也是错误的地址
            fragment.viewModel.savePlace(place)
        }
        return holder
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

}