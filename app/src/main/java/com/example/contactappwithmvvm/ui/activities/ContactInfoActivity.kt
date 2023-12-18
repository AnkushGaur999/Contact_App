package com.example.contactappwithmvvm.ui.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactappwithmvvm.R
import com.example.contactappwithmvvm.database.entities.Contact
import com.example.contactappwithmvvm.databinding.ActivityContactInfoBinding
import com.example.contactappwithmvvm.viewmodels.ContactViewModel


class ContactInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactInfoBinding
    private var contact:Contact? = null
    private val viewModel by lazy {
        ContactViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_info)

        val contactId: Long = intent.getLongExtra("contactId", -1)
        viewModel.getContact(contactId)

        viewModel.userDetails.observe(this){
            contact ->

            contact?.let {
                this.contact = contact
                binding.contact = contact
            }
        }

        initViews()
    }

    private fun initViews(){
        binding.deleteItem.setOnClickListener {
            deleteItem()
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.editButton.setOnClickListener {
            val intent = Intent(this, UpdateContactActivity::class.java)
            intent.putExtra("contactId", contact?.id)
            startActivity(Intent(intent))
        }
    }

    private fun deleteItem(){
        viewModel.deleteContact(contact!!)
        Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_LONG).show()
        onBackPressedDispatcher.onBackPressed()
    }

}
