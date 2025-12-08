package com.example.demoslide8

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.text.isNotEmpty
import kotlin.text.toFloatOrNull

@Composable
fun AddScreen(
    product: Product?,
    onSubmit: (Product) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    // nếu price là Int/Double thì dùng String để nhập
    var priceText by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var image by remember { mutableStateOf(product?.image ?: "") }

    Column(Modifier.padding(16.dp)) {

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = { Text("Giá sản phẩm") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = image,
            onValueChange = { image = it },
            label = { Text("Link ảnh") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && priceText.isNotEmpty()) {

                    // chuyển String -> số
                    val priceNumber: Float = priceText.toFloatOrNull() ?: 0f

                    val newProduct = Product(
                        id = product?.id ?: System.currentTimeMillis().toString(),
                        name = name,
                        price = priceNumber,
                        image = image
                    )

                    onSubmit(newProduct)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lưu")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hủy")
        }
    }
}
