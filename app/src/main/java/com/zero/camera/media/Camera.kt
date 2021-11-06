package com.zero.camera.media

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.zero.camera.ui.theme.CameraCapture

@Composable
fun Camera(
    modifier: Modifier = Modifier,
    setUri: (Uri) -> Unit,
    showGallery: () -> Unit
) {
    Box(modifier = modifier) {
        CameraCapture(
            modifier = modifier,
            showGallery = showGallery,
            onImageFile = { file -> setUri(file.toUri()) }
        )
    }
}