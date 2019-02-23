package com.ahmedabdelmeged.simplecontactsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.ahmedabdelmeged.simplecontactsapp.db.AppDatabase
import com.ahmedabdelmeged.simplecontactsapp.db.Contact
import java.lang.Exception
import java.util.concurrent.Executors

/**
 * Repository to interact with [AppDatabase] to provide a clean
 * methods to the rest of the app and hide the IO complexity.
 */
class ContactsRepository(private val db: AppDatabase) {

    /**
     * Executor to do database operations and background thread to avoid locking the main thread.
     */
    private val executor = Executors.newSingleThreadExecutor()

    fun loadContacts(): LiveData<List<Contact>> {
        return db.contactsDao().loadContacts()
    }

    fun insertContact(contact: Contact) {
        executor.execute {
            try {
                db.contactsDao().insertContact(contact)
            } catch (ex: Exception) {
                //TODO in production provide better error handling
                Log.e("ContactsRepository", "Couldn't insert contact: $contact")
            }
        }
    }

    fun updateContact(contact: Contact) {
        executor.execute {
            try {
                db.contactsDao().updateContact(contact)
            } catch (ex: Exception) {
                //TODO in production provide better error handling
                Log.e("ContactsRepository", "Couldn't update contact: $contact")
            }
        }
    }

    fun deleteUser(contact: Contact) {
        executor.execute {
            try {
                db.contactsDao().deleteContact(contact)
            } catch (ex: Exception) {
                //TODO in production provide better error handling
                Log.e("ContactsRepository", "Couldn't delete contact: $contact")
            }
        }
    }

}