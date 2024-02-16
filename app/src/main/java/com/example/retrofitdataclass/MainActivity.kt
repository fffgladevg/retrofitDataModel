package com.example.retrofitdataclass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitdataclass.databinding.ActivityMainBinding
import com.example.retrofitdataclass.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.request.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val api = retrofit.create(MainApi::class.java)
                val model = api.getWeatherData(
                    "ab0ee26629c3497987160919241601",
                    "London",
                    "3",
                    "no",
                    "no"
                )
                binding.temp.text = model.current.temp_c.toString()
                binding.date.text = model.location.name
            }
        }
    }
}