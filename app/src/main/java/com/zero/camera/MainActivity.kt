package com.zero.camera

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zero.camera.media.Camera
import com.zero.camera.media.CapturedImage
import com.zero.camera.media.EMPTY_IMAGE_URI
import com.zero.camera.media.ImageGallery
import com.zero.camera.permission.Permission
import com.zero.camera.storage.Storage
import com.zero.camera.ui.Blue400
import com.zero.camera.ui.Blue500
import com.zero.camera.ui.CameraTheme
import com.zero.camera.ui.SlideTopToBottomAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTheme {
                Permission(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    Box(
                        modifier = Modifier.background(
                            brush = Brush.horizontalGradient(colors = listOf(Blue400, Blue500))
                        )
                    ) { MainContent() }
                }
            }
        }
    }
}

@Composable
fun MainContent() {

    var hasCapturedImage by rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf(EMPTY_IMAGE_URI) }
    val coroutineScope = rememberCoroutineScope()

    fun removeCapturedImage() {
        hasCapturedImage = false
        coroutineScope.launch {
            delay(300)
            imageUri = EMPTY_IMAGE_URI
        }
    }

    var showGallerySelect by remember { mutableStateOf(false) }

    if (showGallerySelect) {
        ImageGallery(
            onImageUri = { uri ->
                showGallerySelect = false
                imageUri = uri
            }
        )
    } else {
        ConstraintLayout {
            val (camera, capturedImage) = createRefs()
            Camera(
                setUri = {
                    hasCapturedImage = true
                    imageUri = it
                },
                showGallery = { showGallerySelect = true },
                modifier = Modifier
                    .constrainAs(camera) {
                        top.linkTo(anchor = parent.top)
                        end.linkTo(anchor = parent.end)
                        bottom.linkTo(anchor = parent.bottom)
                        start.linkTo(anchor = parent.start)
                    }
            )
            SlideTopToBottomAnimation(animate = hasCapturedImage) {
                CapturedImage(
                    imageUri = imageUri,
                    storeImage = {
                        Storage.storeToExternalDirectory("test_image.jpg", imageUri)
                        removeCapturedImage()
                    },
                    resetImage = { removeCapturedImage() },
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(capturedImage) {
                            top.linkTo(anchor = parent.top)
                            start.linkTo(anchor = parent.start)
                        }
                )
            }
        }
    }
}