package com.example.newsbreezeajaykumar.api

import com.example.newsbreezeajaykumar.models.ApiRespone
import com.example.newsbreezeajaykumar.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        val api by lazy {
            retrofit.create(NewsApiInterface::class.java)
        }
    }
}