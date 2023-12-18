package com.example.contactappwithmvvm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactappwithmvvm.database.entities.Contact
import com.example.contactappwithmvvm.databinding.ContactItemBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contactList = ArrayList<Contact>()
    private var onContactClickListener: OnContactClickListener? = null

    fun setListener(listener: OnContactClickListener) {
        onContactClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(contactList: List<Contact>) {
        if (this.contactList.isNotEmpty()) this.contactList.clear()
        this.contactList.addAll(contactList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onContactClickListener?.onContactClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }


    interface OnContactClickListener {

        fun onContactClick(contact: Contact)
    }
}