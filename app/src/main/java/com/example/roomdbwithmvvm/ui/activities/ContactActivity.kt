package com.example.roomdbwithmvvm.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomdbwithmvvm.R
import com.example.roomdbwithmvvm.adapters.ContactAdapter
import com.example.roomdbwithmvvm.database.entities.Contact
import com.example.roomdbwithmvvm.databinding.ActivityContactBinding
import com.example.roomdbwithmvvm.repositories.ContactRepo
import com.example.roomdbwithmvvm.viewmodels.ContactViewModel

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    private lateinit var viewModel: ContactViewModel

    private val adapter by lazy {
        ContactAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        binding.contactAdapter = adapter
        binding.contactViewModel = viewModel
        binding.floatingActionButton.setOnClickListener{ addContact() }


      //  addItemForFirstTime()
        setObserver()

    }

    private fun setObserver(){

        viewModel.allContacts.observe(this){
            list->
            if(list!=null && list.isNotEmpty()){
                adapter.updateList(list)
            }
        }
    }

    private fun addContact(){
        startActivity(Intent(this, AddContactActivity::class.java))
    }
}