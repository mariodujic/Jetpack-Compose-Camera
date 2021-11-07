package com.zero.camera.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@Composable
fun rememberCapturedImageState(hasCapturedImage: Boolean = false): CapturedImageState {
    val coroutineScope = rememberCoroutineScope()
    return rememberSaveable(saver = CapturedImageState.saver()) {
        CapturedImageState(hasCapturedImage, coroutineScope)
    }
}

class CapturedImageState(
    hasCapturedImage: Boolean = false,
    private val coroutineScope: CoroutineScope? = null
) {

    var hasImage by mutableStateOf(hasCapturedImage)
        private set

    var imageUri by mutableStateOf(EMPTY_IMAGE_URI)
        private set

    fun setCapturedImage(imageUri: Uri) {
        hasImage = true
        this@CapturedImageState.imageUri = imageUri
    }

    fun removeCapturedImage() {
        hasImage = false
        coroutineScope?.launch {
            delay(300)
            imageUri = EMPTY_IMAGE_URI
        }
    }

    companion object {
        fun saver(): Saver<CapturedImageState, *> = Saver(
            save = { it.hasImage },
            restore = { CapturedImageState(it) }
        )
    }
}