package com.example.task16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task16.data.ContactItem
import com.example.task16.util.GSON_PHONE_DIRECTORY_
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonPhoneDirectory = GSON_PHONE_DIRECTORY_
        val gson = Gson()
        val listOfContacts = object : TypeToken<List<ContactItem>>() {}.type
        val contacts: List<ContactItem> = gson.fromJson(jsonPhoneDirectory, listOfContacts)

        val listOfContactsWithInformation = contacts.forEachIndexed  { idx, contact -> println("> Item ${idx}:\n${contact}") }
        Timber.d(listOfContactsWithInformation.toString())
    }
}