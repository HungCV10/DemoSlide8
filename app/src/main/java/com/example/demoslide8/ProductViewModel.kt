package com.example.demoslide8

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel(){
    // trạng thái danh sách sản phẩm
    private val lstProduct = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = lstProduct

    // trạng thái sản phẩm được chọn
    private  val selectProduct = MutableLiveData<Product?>()
    val slProduct: LiveData<Product?> = selectProduct

    init {
        getAllProduct()
    }

    fun getAllProduct(){
        viewModelScope.launch{
            try {
                val response = RetrofitService.productService.getList()
                if(response.isSuccessful){
                    // nếu có dữ liệu thì trả về list kiểu responseProduct,
                    // nếu không thì trả về list rỗng
                    lstProduct.value = response.body()?.map{it.toProduct()} ?:emptyList()
                    Log.e("getAllProduct", "danh sách: ${lstProduct.value}")
                }
            }catch (e: Exception){
                Log.e("getAllProduct", "Lỗi: ${e.message}")
            }
        }
    }

    fun getDetail(id: String){
        viewModelScope.launch{
            try {
                val response = RetrofitService.productService.getDetailProduct(id)
                if(response.isSuccessful){
                    // nếu có dữ liệu thì trả về đối tượng kiểu Product,
                    selectProduct.value = response.body()?.toProduct()
                    Log.e("getDetail", "chi tiết: ${ selectProduct.value}")
                }
            }catch (e: Exception){
                Log.e("getDetail", "Lỗi: ${e.message}")
            }
        }
    }

    // Thêm sản phẩm
    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.productService.addProduct(product)

                if (response.isSuccessful) {
                    val newProduct = response.body()?.toProduct()
                    if (newProduct != null) {
                        val current = lstProduct.value?.toMutableList() ?: mutableListOf()
                        current.add(newProduct)
                        lstProduct.value = current
                        Log.d("ProductViewModel", "Thêm thành công: $newProduct")
                    }
                } else {
                    Log.e("ProductViewModel", "Lỗi API add: ${response.code()}")
                }
            } catch (e: java.lang.Exception) {
                Log.e("ProductViewModel", "Lỗi add: ${e.message}")
            }
        }
    }

    // Cập nhật sản phẩm
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.productService.updateProduct(product.id, product)

                if (response.isSuccessful) {
                    val updatedProduct = response.body()?.toProduct()
                    if (updatedProduct != null) {
                        val updatedList = lstProduct.value?.map {
                            if (it.id == updatedProduct.id) updatedProduct else it
                        } ?: listOf(updatedProduct)

                        lstProduct.value = updatedList
                        selectProduct.value = updatedProduct

                        Log.d("ProductViewModel", "Update thành công: $updatedProduct")
                    }
                } else {
                    Log.e("ProductViewModel", "Lỗi API update: ${response.code()}")
                }
            } catch (e: java.lang.Exception) {
                Log.e("ProductViewModel", "Lỗi update: ${e.message}")
            }
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.productService.deleteProduct(id)
                if (response.isSuccessful) {
                    val currentList = lstProduct.value ?: emptyList()
                    lstProduct.value = currentList.filter { it.id != id }
                    selectProduct.value = null
                }
            } catch (e: java.lang.Exception) {
                Log.e("deleteProduct", "Lỗi delete: ${e.message}")
            }
        }
    }
}