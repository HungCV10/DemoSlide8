package com.example.demoslide8

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.6:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

}