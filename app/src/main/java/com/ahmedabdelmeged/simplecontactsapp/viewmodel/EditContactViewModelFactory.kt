package com.ahmedabdelmeged.simplecontactsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository
import com.ahmedabdelmeged.simplecontactsapp.db.Contact

/**
 * Factory class to create [EditContactViewModelFactory] to inject the [ContactsRepository] to it's constructor
 * for better testing.
 */
class EditContactViewModelFactory(private val repository: ContactsRepository, private val contact: Contact?)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditContactViewModel(repository, contact) as T
    }

}