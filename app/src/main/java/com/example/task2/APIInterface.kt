package com.example.task2

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/api/v1/products.json")
    fun getProductInfo() : Call<List<ResponseData>>

}