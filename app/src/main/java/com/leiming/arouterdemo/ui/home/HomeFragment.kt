package com.leiming.arouterdemo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.leiming.arouterdemo.AppApplication
import com.leiming.arouterdemo.MainActivity
import com.leiming.arouterdemo.databinding.FragmentHomeBinding
import com.leiming.arouterdemo.ui.home.network.RequestService
import com.leiming.network.ClientUtil
import com.leiming.network.database.AppDataBase
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private val BASE_URL = "https://jisutianqi.market.alicloudapi.com"

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

        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        binding.button.setOnClickListener {
            val reqApi =
                ClientUtil().buildRestAdapter(requireContext().applicationContext).baseUrl(BASE_URL)
                    .build()
                    .create(RequestService::class.java)

            println("start request")
            GlobalScope.launch(handler) {
                val result = reqApi.getDatas()
                Log.i("launch", "onResponse:${result}")
            }
        }

        return binding.root
    }
}