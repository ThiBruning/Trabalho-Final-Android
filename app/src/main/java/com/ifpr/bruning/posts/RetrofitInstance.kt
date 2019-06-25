package com.ifpr.bruning.posts

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.17.0.1:8000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}