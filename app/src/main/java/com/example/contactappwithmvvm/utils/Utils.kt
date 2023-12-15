package com.example.contactappwithmvvm.utils

import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * This method is used for get bitmap object from intent object.
 */
fun getBitmapFromIntent(data: Intent): Bitmap{
    val bytes = ByteArrayOutputStream()
    val thumbnails = data.extras?.get("data") as Bitmap
    thumbnails.compress(Bitmap.CompressFormat.JPEG, 90 , bytes)

    val destination = File(
        Environment.getExternalStorageDirectory(),
        System.currentTimeMillis().toString() + ".jpg"
    )

    val fo: FileOutputStream
    try {
        destination.createNewFile()
        fo = FileOutputStream(destination)
        fo.write(bytes.toByteArray())
        fo.close()
    } catch (e: FileNotFoundException) {
       throw IllegalAccessException(e.message)
    } catch (e: IOException) {
        throw IllegalAccessException(e.message)
    }

    return thumbnails
}
