package com.example.contactappwithmvvm.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contactappwithmvvm.database.entities.Contact


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY first_name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE id LIKE:id")
    fun getContactById(id: Long): Contact

}