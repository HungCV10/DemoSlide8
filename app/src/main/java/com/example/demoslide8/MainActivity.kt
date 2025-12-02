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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.demoslide8.ui.theme.DemoSlide8Theme

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoSlide8Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }

                ProductListScreen(productViewModel)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product){
    Card() {
        Column(
            modifier = Modifier.fillMaxWidth().
            padding(20.dp).background(Color.Green)
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
fun ProductListScreen(viewModel: ProductViewModel){
    // danh sách từ live data
    val lstProduct by viewModel.products.observeAsState(emptyList())
    // sản phẩm được chọn
    //val slProduct by viewModel.slProduct.observeAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        items(lstProduct){product->
            ProductItem(product)
        }
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