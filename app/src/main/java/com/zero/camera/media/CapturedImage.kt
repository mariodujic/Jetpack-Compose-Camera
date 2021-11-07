package com.zero.camera.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save
import compose.icons.feathericons.XCircle

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CapturedImage(
    imageUri: Uri,
    storeImage: () -> Unit,
    resetImage: () -> Unit
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(imageUri),
            contentDescription = "Captured image"
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = resetImage
            ) {
                Icon(
                    imageVector = FeatherIcons.XCircle,
                    contentDescription = "Discard image",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
            Spacer(modifier = Modifier.width(22.dp))
            IconButton(
                onClick = storeImage
            ) {
                Icon(
                    imageVector = FeatherIcons.Save,
                    contentDescription = "Save image",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
        }
    }
}