package com.ahmedabdelmeged.simplecontactsapp.db

import android.content.Context
import androidx.room.*
import com.ahmedabdelmeged.simplecontactsapp.util.SingletonHolder

@Database(entities = [Contact::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    /**
     * Initialize [AppDatabase] lazily as singleton to use on demand. For a production
     * app we might use dependency injection to inject the database to where we want to use it.
     */
    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                AppDatabase::class.java, "contacts.db")
                .build()
    })

}