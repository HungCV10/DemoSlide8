package com.example.demoslide8

import com.google.gson.annotations.SerializedName

// khai báo các giá trị trả về khi gọi api
data class ProductResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Float,
    @SerializedName("image") val image: String,
)

fun ProductResponse.toProduct(): Product{
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        image = this.image
    )
}