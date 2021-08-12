package com.example.task16.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task16.Adapter
import com.example.task16.data.ContactItem
import com.example.task16.databinding.ActivityMainBinding
import com.example.task16.util.JSON_PHONE_DIRECTORY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var contacts: List<ContactItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        contacts = parseJsonInList()
        adapter = Adapter(contacts)
        initRecyclerView(adapter)
        filterForList(adapter, contacts)
    }

    private fun filterForList(adapter: Adapter, contacts: List<ContactItem>) {
        mBinding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val etSearchBarText = mBinding.etSearchBar.text

                if (etSearchBarText.isEmpty()) {
                    adapter.phoneItems = contacts

                } else {
                    adapter.phoneItems = contacts.filter {
                        it.name.startsWith(etSearchBarText.toString(), true)
                                || it.name.contains(etSearchBarText.toString(), true)
                    } as ArrayList
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun parseJsonInList() : List<ContactItem> {
        val phonesJson = JSON_PHONE_DIRECTORY
        val gson = Gson()
        val listOfContacts = object : TypeToken<List<ContactItem>>() {}.type
        return gson.fromJson(phonesJson, listOfContacts)
    }

    private fun initRecyclerView(adapter: Adapter) {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter
    }
}