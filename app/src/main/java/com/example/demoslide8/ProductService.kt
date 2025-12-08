package com.example.demoslide8

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("/product")
    suspend fun getList(): Response<List<ProductResponse>>
    @GET("/product/{id}")
    suspend fun getDetailProduct(@Path("id") id: String): Response<Product>

    // Thêm sản phẩm
    @POST("product")
    suspend fun addProduct(@Body product: Product): Response<ProductResponse>

    // Cập nhật sản phẩm
    @PUT("product/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body product: Product
    ): Response<ProductResponse>

    // delete sản phẩm
    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<ProductResponse>

}