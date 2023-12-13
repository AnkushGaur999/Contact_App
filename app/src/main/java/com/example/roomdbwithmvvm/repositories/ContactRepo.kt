package com.example.roomdbwithmvvm.repositories

import androidx.lifecycle.LiveData
import com.example.roomdbwithmvvm.database.dao.ContactDao
import com.example.roomdbwithmvvm.database.entities.Contact

class ContactRepo (private val contactDao:ContactDao) {

    val getAllContacts: LiveData<List<Contact>> get() = contactDao.getAllContacts()

    fun addContact(contact: Contact){
        contactDao.addContact(contact)
    }

    fun updateContact(contact: Contact){
        contactDao.updateContact(contact)
    }

    fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }

    fun deleteAllContacts(){
        contactDao.deleteAllContacts()
    }
}