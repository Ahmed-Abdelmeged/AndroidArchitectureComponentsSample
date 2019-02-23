package com.ahmedabdelmeged.simplecontactsapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ahmedabdelmeged.simplecontactsapp.R
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository
import com.ahmedabdelmeged.simplecontactsapp.db.AppDatabase
import com.ahmedabdelmeged.simplecontactsapp.db.Contact
import com.ahmedabdelmeged.simplecontactsapp.viewmodel.EditContactViewModel
import com.ahmedabdelmeged.simplecontactsapp.viewmodel.EditContactViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_contact.*
import android.util.Patterns

class EditContactActivity : AppCompatActivity() {

    private lateinit var viewModel: EditContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        //The contact object might be come from intent or from savedInstanceState
        val contact: Contact? = intent?.getParcelableExtra(ARG_CONTACT)
                ?: savedInstanceState?.getParcelable(ARG_CONTACT)

        //Setup Activity title based on the the starting mode. Check [newIntent()]
        val title = if (contact == null) R.string.add_contact_title else R.string.edit_contact_title
        setTitle(title)

        setupViewWithContact(contact)

        //Setup ViewModel
        val db = AppDatabase.getInstance(applicationContext)
        val factory = EditContactViewModelFactory(ContactsRepository(db), contact)
        viewModel = ViewModelProviders.of(this, factory).get(EditContactViewModel::class.java)

        saveContact.setOnClickListener { saveContact() }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable(ARG_CONTACT, viewModel.contact)
    }

    private fun setupViewWithContact(contact: Contact?) {
        contact?.let {
            nameEditText.setText(it.name)
            phoneNumberEditText.setText(it.phoneNumber)
        }
    }

    private fun saveContact() {
        val name = nameEditText.text.toString()
        val phoneNumber = phoneNumberEditText.text.toString()
        if (validContact(name, phoneNumber)) {
            viewModel.saveContact(name, phoneNumber)
            //TODO we can improve here by providing a feedback when the contact is saved
            finish()
        }
    }

    private fun validContact(name: String?, phoneNumber: String): Boolean {
        val validName: Boolean
        val validPhoneNumber: Boolean

        if (name.isNullOrEmpty()) {
            validName = false
            nameTextInputLayout.error = getString(R.string.enter_valid_name)
        } else {
            validName = true
            nameTextInputLayout.error = null
        }

        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            validPhoneNumber = false
            phoneNumberTextInputLayout.error = getString(R.string.enter_valid_phone_number)
        } else {
            validPhoneNumber = true
            phoneNumberTextInputLayout.error = null
        }

        return validName && validPhoneNumber
    }

    companion object {
        private const val ARG_CONTACT = "arg_contact"

        /**
         * The [EditContactActivity] has two modes depending on the starting intent.
         * If we have a contact object in the bundle that mean edit mode where we will be editing
         * an existing contact otherwise create a new contact
         */
        fun newIntent(activity: Activity, contact: Contact? = null): Intent {
            val intent = Intent(activity, EditContactActivity::class.java)
            intent.putExtra(ARG_CONTACT, contact)
            return intent
        }
    }

}