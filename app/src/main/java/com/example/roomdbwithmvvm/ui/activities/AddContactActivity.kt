package com.example.roomdbwithmvvm.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roomdbwithmvvm.R
import com.example.roomdbwithmvvm.database.entities.Contact
import com.example.roomdbwithmvvm.databinding.ActivityAddContactBinding
import com.example.roomdbwithmvvm.viewmodels.ContactViewModel

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    private lateinit var viewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact)
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        binding.viewModel = viewModel
        setObserver()
    }

    private fun setObserver() {

        viewModel.addButtonClick.observe(this) {
            if (validateData()) {
                val contact = Contact(
                    id = null,
                    firstName = binding.firstNameET.text.toString(),
                    middleName = binding.middleNameET.text.toString(),
                    lastName = binding.lastNameET.text.toString(),
                    contactNumber = binding.mobileNoET.text.toString(),
                    email = binding.emailET.text.toString(),
                    address = binding.cityET.text.toString() + " " + binding.stateET.text.toString()
                )
                addContact(contact)
            }

        }

    }

    private fun validateData(): Boolean {

        if (binding.firstNameET.text.isEmpty()) {
            showToast("First name can't be blank")
            return false
        } else if (binding.lastNameET.text.isEmpty()) {
            showToast("Last name can't be blank")
            return false
        } else if (binding.mobileNoET.text.isEmpty()) {
            showToast("Phone number is required")
            return false
        } else if (binding.emailET.text.isEmpty()) {
            showToast("Email is required")
            return false
        } else if (binding.cityET.text.isEmpty()) {
            showToast("City is required")
            return false
        } else if (binding.stateET.text.isEmpty()) {
            showToast("State is required")
            return false
        }
        return true

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun addContact(contact: Contact) {
        viewModel.addContact(contact)
        Toast.makeText(this, "Contact Add successfully", Toast.LENGTH_LONG).show()
        onBackPressedDispatcher.onBackPressed()
    }

}