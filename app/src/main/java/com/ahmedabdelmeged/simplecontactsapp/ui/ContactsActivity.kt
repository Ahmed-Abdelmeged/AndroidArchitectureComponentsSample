package com.ahmedabdelmeged.simplecontactsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmedabdelmeged.simplecontactsapp.data.ContactsRepository
import com.ahmedabdelmeged.simplecontactsapp.db.AppDatabase
import com.ahmedabdelmeged.simplecontactsapp.viewmodel.ContactsViewModel
import com.ahmedabdelmeged.simplecontactsapp.viewmodel.ContactsViewModelFactory
import kotlinx.android.synthetic.main.activity_contacts.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.ahmedabdelmeged.simplecontactsapp.R

class ContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        //Setup Recycler
        val adapter = ContactsAdapter {
            val intent = EditContactActivity.newIntent(this, it)
            startActivity(intent)
        }
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        //Setup ViewModel
        val db = AppDatabase.getInstance(applicationContext)
        val factory = ContactsViewModelFactory(ContactsRepository(db))
        val viewModel = ViewModelProviders.of(this, factory).get(ContactsViewModel::class.java)
        viewModel.contactsLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        addContact.setOnClickListener { startActivity(EditContactActivity.newIntent(this)) }

        //Setup swipe to delete
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.contactsLiveData.value?.getOrNull(viewHolder.adapterPosition)?.let {
                    viewModel.deleteContact(it)
                }
            }
        }

        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recycler)
    }

}