package com.ahmedabdelmeged.simplecontactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository
import com.ahmedabdelmeged.simplecontactsapp.db.Contact

class ContactsViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    val contactsLiveData: LiveData<List<Contact>> = contactsRepository.loadContacts()

    fun deleteContact(contact: Contact) {
        contactsRepository.deleteUser(contact)
    }

}