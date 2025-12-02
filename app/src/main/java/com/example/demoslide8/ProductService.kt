package com.example.demoslide8

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/product")
    suspend fun getList(): Response<List<ProductResponse>>
}