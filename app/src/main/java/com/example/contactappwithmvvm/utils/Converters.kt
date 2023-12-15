package com.example.contactappwithmvvm.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

/**
 *  This class is used for convert all custom data type to another data type.
 */
class Converters{

    @TypeConverter
    fun toBitmap(bytes: ByteArray?):Bitmap?{
        return BitmapFactory.decodeByteArray(bytes, 0, bytes?.size!!)
    }
    @TypeConverter
    fun toByteArray(bitmap: Bitmap?): ByteArray?{
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()

    }

}