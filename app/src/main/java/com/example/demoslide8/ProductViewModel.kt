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
                val reponse = RetrofitService.productService.getList()
                if(reponse.isSuccessful){
                    // nếu có dữ liệu thì trả về list kiểu responseProduct,
                    // nếu không thì trả về list rỗng
                    lstProduct.value = reponse.body()?.map{it.toProduct()} ?:emptyList()
                    Log.e("getAllProduct", "danh sách: ${lstProduct.value}")
                }
            }catch (e: Exception){
                Log.e("getAllProduct", "Lỗi: ${e.message}")
            }
        }
    }
}