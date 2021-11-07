package com.zero.camera.storage

import android.net.Uri
import android.os.Environment
import androidx.core.net.toFile
import java.io.File
import java.io.FileOutputStream

object Storage {

    fun storeToExternalDirectory(fileName: String, uri: Uri) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File("$root/$fileName")
        FileOutputStream(file).use {
            it.write(uri.toFile().readBytes())
        }
    }
}