package com.leiming.arouterdemo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.leiming.arouterdemo.databinding.FragmentHomeBinding
import com.leiming.arouterdemo.ui.home.network.RequestService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//http://t.weather.sojson.com/api/weather/city/101030100
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

//    val retrofit: Retrofit = Retrofit.Builder()
//        .client(OkHttpClient())
//        .baseUrl("http://t.weather.sojson.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .build()

    val BASE_URL = "http://jisutianqi.market.alicloudapi.com"

    //    val reqApi by lazy {
//        val retrofit: = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
//        return@lazy retrofit.create(RequestService::class.java)
//    }
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val textView: TextView = binding.textHome

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        binding.button.setOnClickListener {
            val reqApi = retrofit.create(RequestService::class.java)
//            ARouter.getInstance().build("/subactivity/TestActivity").navigation()
            println("start request")
            GlobalScope.launch {
                val result = reqApi.getDatas()
                Log.i("launch", "onResponse:${result}")
            }

//            GlobalScope.launch(Dispatchers.Unconfined) {
//                Log.d("AA", "协程初始化完成，时间: " + System.currentTimeMillis())
//                for (i in 1..3) {
//                    Log.d("AA", "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
//                }
//                delay(500)
//                for (i in 1..3) {
//                    Log.d("AA", "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
//                }
//            }
        }

        return binding.root
    }
}