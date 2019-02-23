package com.ahmedabdelmeged.simplecontactsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository
import com.ahmedabdelmeged.simplecontactsapp.db.Contact
import java.util.*

class EditContactViewModel(private val contactsRepository: ContactsRepository, val contact: Contact? = null) : ViewModel() {

    fun saveContact(name: String, phoneNumber: String) {
        if (contact == null) {
            contactsRepository.insertContact(Contact(name = name, phoneNumber = phoneNumber, date = Date()))
        } else {
            contactsRepository.updateContact(contact.copy(name = name, phoneNumber = phoneNumber))
        }
    }

}