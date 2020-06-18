package com.leiming.arouterdemo.ui.home.subactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.leiming.arouterdemo.databinding.ActivityTestBinding
import com.leiming.network.database.AppDataBase
import com.leiming.network.database.Logging
import java.lang.Exception

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val dataBase: AppDataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "Logging.db"
        )
            .allowMainThreadQueries()
            .build()
        val list: List<Logging> = dataBase.getLoggingDao().getAllLog()
        try {
            binding.network.text = "URL = " + list.get(0).url
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}