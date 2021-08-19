package com.example.task16.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task16.Adapter
import com.example.task16.data.ContactItem
import com.example.task16.databinding.ActivityMainBinding
import com.example.task16.util.PHONES_JSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val SHARED_PREF = "shared preferences name"
private const val SHARED_PREF_KEY = "shared preferences"

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var contacts: List<ContactItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        contacts = parseContacts()
        initRecyclerView()
        setFilterChangedListener()
        loadFilterValue()
    }

    private fun setFilterChangedListener() {
        mBinding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContacts()
                saveFilterValue(mBinding.etSearchBar.toString())
            }
        })
    }

    private fun parseContacts() : List<ContactItem> {
        val gson = Gson()
        val tokenForParse = object : TypeToken<List<ContactItem>>() {}.type
        return gson.fromJson(PHONES_JSON, tokenForParse)
    }

    private fun initRecyclerView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        mBinding.recyclerView.adapter = adapter
    }

    private fun saveFilterValue(etSearchBarTxt: String) {
        val sharedPreferences: SharedPreferences =
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        sharedPreferences.edit(commit = true) { putString(SHARED_PREF_KEY, etSearchBarTxt) }
    }

    private fun loadFilterValue() {
        val sharedPreferences: SharedPreferences =
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val text = sharedPreferences.getString(
                SHARED_PREF_KEY,
                null)
        mBinding.etSearchBar.setText(text)
        filterContacts()
    }

    private fun filterContacts() {
        val textForFilter = mBinding.etSearchBar.text.toString()
        adapter.submitList(contacts.filter {
            it.name.startsWith(textForFilter, true)
                    || it.name.contains(textForFilter, true)
        })
    }
}