package com.example.sunnyweather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.R
import com.example.sunnyweather.ui.weather.WeatherActivity
import com.example.sunnyweather.util.Utils
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @author: Fangjie
 * @Date: Created in 16:01 2020/7/4
 * @version:
 * @Description:
 */
class PlaceFragment : Fragment() {

    //使用懒加载技术获取viewModel
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //如果当前已有存储的地址数据，就获取已存储的数据
        if (viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            Utils.startActivity<WeatherActivity>(context!!) {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            //结束当前fragment
            activity?.finish()
            return
        }

        //使用recyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        //adapter保存了数据的引用，后面更改数据，只需要调用adapter.notifyDataSetChanged()方法通知更新
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                //更新数据
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}

