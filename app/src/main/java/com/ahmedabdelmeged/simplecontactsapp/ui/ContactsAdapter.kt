package com.ahmedabdelmeged.simplecontactsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedabdelmeged.simplecontactsapp.R
import com.ahmedabdelmeged.simplecontactsapp.db.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter(private val onItemClick: (Contact) -> Unit)
    : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(Contact.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.name.text = contact.name
        holder.fullNumber.text = contact.phoneNumber

        holder.itemView.setOnClickListener { onItemClick(contact) }
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_name
        val fullNumber: TextView = view.contact_phone_number
    }

}