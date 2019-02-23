package com.ahmedabdelmeged.simplecontactsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository

/**
 * Factory class to create [ContactsViewModel] to inject the [ContactsRepository] to it's constructor
 * for better testing.
 */
class ContactsViewModelFactory(private val repository: ContactsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactsViewModel(repository) as T
    }

}