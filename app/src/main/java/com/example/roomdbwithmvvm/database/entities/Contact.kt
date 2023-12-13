package com.example.roomdbwithmvvm.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact_table")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "middle_name")
    val middleName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "contact_number")
    val contactNumber: String,

    @ColumnInfo(name = "email")
    val email:String,

    @ColumnInfo(name = "address")
    val address: String?,



)
