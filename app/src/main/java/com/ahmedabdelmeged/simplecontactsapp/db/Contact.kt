package com.ahmedabdelmeged.simplecontactsapp.db

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Contact.CONTACTS_TABLE)
data class Contact(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,

        val name: String,

        @ColumnInfo(name = "phone_number")
        val phoneNumber: String,

        val date: Date) : Parcelable {

    companion object {
        //Contacts SQL table name.
        const val CONTACTS_TABLE = "contacts_table"

        /**
         * Diff callback used in [] to calculate
         * the difference between contacts in a background thread before adding it to the adapter to
         * have better animation and performance.
         */
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return newItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return newItem == oldItem
            }
        }
    }

}