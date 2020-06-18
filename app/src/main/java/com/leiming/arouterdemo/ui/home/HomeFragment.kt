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
import com.leiming.arouterdemo.databinding.FragmentHomeBinding
import com.leiming.arouterdemo.ui.home.network.RequestService
import com.leiming.network.ClientUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    //    private val BASE_URL = "https://jisutianqi.market.alicloudapi.com"
    private val BASE_URL = "http://www.weather.com.cn"

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
                    .build().create(RequestService::class.java)

            println("start request")
            GlobalScope.launch(handler) {
                val result = reqApi.getDatas("101010100")
                Log.i("launch", "onResponse:${result}")
            }

        }

        return binding.root
    }
}