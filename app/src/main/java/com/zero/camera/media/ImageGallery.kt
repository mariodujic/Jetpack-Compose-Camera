package com.zero.camera.media

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.zero.camera.permission.Permission

@Composable
fun ImageGallery(onImageUri: (Uri) -> Unit) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_IMAGE_URI)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            Permission(
                permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
                rationale = "You want to read from photo gallery, so I'm going to have to ask for permission.",
                permissionNotAvailableContent = { GalleryPermissionNotAvailable(onImageUri = onImageUri) },
                content = { LaunchGallery() }
            )
        }
        else -> LaunchGallery()
    }
}

@Composable
fun GalleryPermissionNotAvailable(onImageUri: (Uri) -> Unit) {
    val context = LocalContext.current
    Column {
        Text("O noes! No Photo Gallery!")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }
            ) {
                Text("Open Settings")
            }
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = { onImageUri(EMPTY_IMAGE_URI) }
            ) {
                Text("Use Camera")
            }
        }
    }
}