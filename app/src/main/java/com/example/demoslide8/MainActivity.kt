package com.example.demoslide8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.demoslide8.ui.theme.DemoSlide8Theme

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoSlide8Theme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        ProductListScreen(
                            productViewModel,
                            onAddClick = { navController.navigate("add") },
                            onEditClick = { navController.navigate("edit")},
                        )
                    }
                    composable("add") {
                        AddScreen(
                            product = null,
                            onSubmit = { newProduct ->
                                productViewModel.addProduct(newProduct)
                                navController.popBackStack()
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // Màn hình sửa (dùng lại AddScreen)
                    composable("edit") {
                        val selectedProduct by productViewModel.slProduct.observeAsState()

                        AddScreen(
                            product = selectedProduct,
                            onSubmit = { updatedProduct ->
                                productViewModel.updateProduct(updatedProduct)
                                navController.popBackStack()   // quay lại list
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    //ProductListScreen(productViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onItemClick: (String)-> Unit){
    Card() {
        Column(
            modifier = Modifier.fillMaxWidth().
            padding(20.dp).background(Color.Green)
                .clickable{onItemClick(product.id)}
        ) {
            AsyncImage(model = product.image,
                contentDescription = "",
                modifier = Modifier.size(100.dp).padding(20.dp)

            )
            Text(text = "Tên : ${product.name}", fontSize = 30.sp)
            Text(text = "Giá: ${product.price}", fontSize = 30.sp)
        }
    }
}

@Composable
fun ProductListScreen(viewModel: ProductViewModel, onAddClick: ()-> Unit,
                      onEditClick: () -> Unit){
    // danh sách từ live data
    val lstProduct by viewModel.products.observeAsState(emptyList())
    // sản phẩm được chọn
    val slProduct by viewModel.slProduct.observeAsState()

    if(slProduct!=null){
        ProductDetailScreen(
            product = slProduct,
            onEditClick = onEditClick,
            onDeleteClick = {
                slProduct?.id?.let { id ->
                    viewModel.deleteProduct(id)
                }
            })
    }else{
        Column() {
            // Nút Thêm
            Button(
                onClick = onAddClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Thêm sản phẩm")
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                items(lstProduct){product->
                    ProductItem(product, onItemClick = {id->viewModel.getDetail(id)})
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(product: Product?, onEditClick: () -> Unit,
                        onDeleteClick: () -> Unit){
    var showDeleteDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth().
        padding(20.dp)
    ) {
        if(product!=null){
            AsyncImage(model = product.image,
                contentDescription = "",
                modifier = Modifier.size(100.dp).padding(20.dp)
            )
            Text(text = "Tên : ${product.name}", fontSize = 30.sp)
            Text(text = "Giá: ${product.price}", fontSize = 30.sp)
            // Nút sửa -> chuyển sang màn "edit"
            Button(onClick = onEditClick) {
                Text("Sửa sản phẩm")
            }
            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { showDeleteDialog = true }) {
                Text("Xóa sản phẩm")
            }
        } else {
            Text(text = "Không có dữ liệu sản phẩm")
        }
    }
    // Dialog xác nhận xóa
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(text = "Xác nhận xóa")
            },
            text = {
                Text(text = "Bạn có chắc muốn xóa sản phẩm này không?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoSlide8Theme {
        Greeting("Android")
    }
}