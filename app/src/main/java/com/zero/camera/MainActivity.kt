package com.zero.camera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.zero.camera.media.Camera
import com.zero.camera.media.CapturedImage
import com.zero.camera.media.EMPTY_IMAGE_URI
import com.zero.camera.media.ImageGallery
import com.zero.camera.ui.theme.Blue400
import com.zero.camera.ui.theme.Blue500
import com.zero.camera.ui.theme.CameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTheme {
                Box(
                    modifier = Modifier.background(
                        brush = Brush.horizontalGradient(colors = listOf(Blue400, Blue500))
                    )
                ) { MainContent() }
            }
        }
    }
}

@Composable
fun MainContent() {
    var imageUri by rememberSaveable { mutableStateOf(EMPTY_IMAGE_URI) }
    if (imageUri != EMPTY_IMAGE_URI) {
        CapturedImage(
            imageUri = imageUri,
            saveImage = {},
            resetImage = { imageUri = EMPTY_IMAGE_URI }
        )
    } else {
        var showGallerySelect by remember { mutableStateOf(false) }
        if (showGallerySelect) {
            ImageGallery(
                onImageUri = { uri ->
                    showGallerySelect = false
                    imageUri = uri
                }
            )
        } else {
            Camera(
                setUri = { imageUri = it },
                showGallery = { showGallerySelect = true }
            )
        }
    }
}