package com.example.roomdbwithmvvm.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdbwithmvvm.database.dao.ContactDao
import com.example.roomdbwithmvvm.database.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        private var INSTANCE: ContactDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ContactDatabase {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_database"
                    ).build()

                }

                return INSTANCE!!
        }

    }
}