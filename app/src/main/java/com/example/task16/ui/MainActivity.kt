package com.example.task16.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task16.Adapter
import com.example.task16.data.ContactItem
import com.example.task16.databinding.ActivityMainBinding
import com.example.task16.util.JSON_PHONES
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
        contacts = parseContacts()
        adapter = Adapter(contacts)
        initRecyclerView(adapter)
        initListFilter(adapter, contacts)
    }

    private fun initListFilter(adapter: Adapter, contacts: List<ContactItem>) {
        mBinding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val etSearchBarText = mBinding.etSearchBar.text
                if (etSearchBarText.isEmpty()) {
                    adapter.setList(contacts)
                } else {
                    adapter.setList(contacts.filter {
                        it.name.startsWith(etSearchBarText.toString(), true)
                                || it.name.contains(etSearchBarText.toString(), true)
                    })
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun parseContacts() : List<ContactItem> {
        val phonesJson = JSON_PHONES
        val gson = Gson()
        val listOfPhones = object : TypeToken<List<ContactItem>>() {}.type
        return gson.fromJson(phonesJson, listOfPhones)
    }

    private fun initRecyclerView(adapter: Adapter) {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter
    }
}