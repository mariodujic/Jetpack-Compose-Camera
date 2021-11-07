package com.zero.camera.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CapturedImage(
    imageUri: Uri,
    storeImage: () -> Unit,
    resetImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.width(100.dp)) {
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = "Captured image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Button(
            onClick = resetImage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) { Text("Remove") }
        Button(
            onClick = storeImage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) { Text("Save") }
    }
}