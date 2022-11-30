package com.example.task2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientRequest {
    val baseURL = "https://makeup-api.herokuapp.com/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}