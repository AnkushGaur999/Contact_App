package com.example.roomdbwithmvvm.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.roomdbwithmvvm.R
import com.example.roomdbwithmvvm.databinding.ActivityContactInfoBinding

class ContactInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_info)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}