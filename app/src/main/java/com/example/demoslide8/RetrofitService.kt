package com.example.demoslide8

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://172.20.10.2:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

}