package com.leiming.arouterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leiming.arouterdemo.databinding.ResultProfileBinding

class ResultProfile : AppCompatActivity() {

    private lateinit var binding: ResultProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResultProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.mybutton.text = "this is my name"
    }
}