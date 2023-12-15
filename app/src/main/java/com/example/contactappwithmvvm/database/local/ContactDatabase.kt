package com.example.contactappwithmvvm.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.contactappwithmvvm.database.dao.ContactDao
import com.example.contactappwithmvvm.database.entities.Contact
import com.example.contactappwithmvvm.utils.Converters

@Database(entities = [Contact::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        private var INSTANCE: ContactDatabase? = null


        /**
         * This static method is use for get contact database instance for storage data into room database.
         */
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