package com.example.contactappwithmvvm.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.contactappwithmvvm.R
import com.example.contactappwithmvvm.adapters.ContactAdapter
import com.example.contactappwithmvvm.database.entities.Contact
import com.example.contactappwithmvvm.databinding.ActivityContactBinding
import com.example.contactappwithmvvm.viewmodels.ContactViewModel


class ContactActivity : AppCompatActivity(), ContactAdapter.OnContactClickListener {

    private lateinit var binding: ActivityContactBinding

    private lateinit var viewModel: ContactViewModel

    private val adapter by lazy {
        ContactAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        adapter.setListener(this)
        binding.contactAdapter = adapter
        binding.contactViewModel = viewModel
        binding.floatingActionButton.setOnClickListener{ openAddContactActivity() }

        setObserver()

    }

    /**
     * Observer all the UI related data.
     *  like: observer the list get from view model and set list into contact adapter.
     *   if list is null of empty, then it will not contact available screen,
     */
    private fun setObserver(){

        viewModel.allContacts.observe(this){
            list->
            if(list!=null && list.isNotEmpty()){
                adapter.updateList(list)
                binding.imageView2.visibility = View.GONE
                binding.contactRV.visibility = View.VISIBLE
            }else{
                binding.contactRV.visibility = View.GONE
                binding.imageView2.visibility = View.VISIBLE
            }
        }
    }

    /**
     *  This function is use for open add new contact screen.
     */
    private fun openAddContactActivity(){
        startActivity(Intent(this, AddContactActivity::class.java))
    }

    /**
     *  This method handles the click on contact item and open contact info screen.
     */
    override fun onContactClick(contact: Contact) {
        val intent = Intent(this, ContactInfoActivity::class.java)
        intent.putExtra("contactId", contact.id)
        startActivity(intent)
    }
}