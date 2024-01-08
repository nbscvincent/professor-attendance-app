package com.shin.myproject.screens.main.mainScreen.home.model

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date
import java.util.Locale

interface ApiService {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}

data class WeatherResponse(
    @SerializedName("dt") val timestamp: Long,
    // Other relevant fields
)


fun getWeatherData(context: Context, param: (Any) -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val call = apiService.getCurrentWeather("Manila", "a57d1c86c5a45722b4e789e8521cf2db")

    call.enqueue(object : Callback<WeatherResponse> {
        override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                val timestamp = weatherResponse?.timestamp ?: 0
                updateUiWithLiveTime(timestamp)
            } else {
                // Handle error
            }
        }

        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            // Handle failure
        }
    })
}

fun updateUiWithLiveTime(timestamp: Long) {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    // Set the time zone to Philippine Standard Time
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Manila")

    val liveTime = dateFormat.format(Date(timestamp * 1000))

    // Update your UI with liveTime
    // For example, you can print it to Logcat or update a TextView
    Log.d("Philippine Time", liveTime)
}