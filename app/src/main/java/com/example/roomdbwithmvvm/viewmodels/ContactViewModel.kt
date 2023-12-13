package com.example.roomdbwithmvvm.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdbwithmvvm.ContactApp
import com.example.roomdbwithmvvm.database.dao.ContactDao
import com.example.roomdbwithmvvm.database.entities.Contact
import com.example.roomdbwithmvvm.database.local.ContactDatabase
import com.example.roomdbwithmvvm.repositories.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {

    private val contactRepo: ContactRepo

    val addButtonClick = MutableLiveData<Boolean>()

    init {
        val contactDatabase: ContactDatabase = ContactDatabase.getInstance(ContactApp.appContext)
        val contactDao: ContactDao = contactDatabase.contactDao()
        contactRepo = ContactRepo(contactDao)
    }

    fun addButton(){
        addButtonClick.value = true
    }

    val allContacts: LiveData<List<Contact>> get() = contactRepo.getAllContacts

    fun addContact(contact: Contact){

        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.addContact(contact)
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact){

        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.deleteContact(contact)
        }
    }

    fun deleteAllContacts(){

        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.deleteAllContacts()
        }
    }


}