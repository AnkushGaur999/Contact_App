package com.example.contactappwithmvvm.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactappwithmvvm.database.dao.ContactDao
import com.example.contactappwithmvvm.database.entities.Contact

class ContactRepo (private val contactDao:ContactDao) {

    val getAllContacts: LiveData<List<Contact>> get() = contactDao.getAllContacts()

    fun addContact(contact: Contact){
        contactDao.addContact(contact)
    }

    fun getContact(id: Long): Contact{
        return contactDao.getContactById(id)
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