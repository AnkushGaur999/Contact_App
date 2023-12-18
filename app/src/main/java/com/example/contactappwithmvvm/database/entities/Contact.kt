package com.example.contactappwithmvvm.database.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "contact_table")
@Parcelize
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "contact_number")
    val contactNumber: String,

    @ColumnInfo(name = "email")
    val email:String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "user_image")
    var image: Bitmap?= null


) : Parcelable
