package com.example.task16.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task16.Adapter
import com.example.task16.data.ContactItem
import com.example.task16.databinding.ActivityMainBinding
import com.example.task16.util.PHONES_JSON
import com.example.task16.util.SHARED_PREF_KEY
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
        adapter = Adapter()
        initRecyclerView()
        initListFilter(adapter, contacts)
        loadState()
    }

    private fun initListFilter(adapter: Adapter, contacts: List<ContactItem>) {
        mBinding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val etSearchBarText = mBinding.etSearchBar.text
                if (etSearchBarText.isEmpty()) {
                    adapter.submitList(contacts)
                } else {
                    listFilter()
                    saveState(etSearchBarText.toString())
                }
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
        mBinding.recyclerView.adapter = adapter
    }

    private fun saveState(etSearchBarTxt: String) {
        val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        sharedPreferences
                .edit()
                .putString(SHARED_PREF_KEY, etSearchBarTxt)
                .apply()
    }

    private fun loadState() {
        val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        mBinding.etSearchBar.setText(sharedPreferences.getString(
                SHARED_PREF_KEY,
                null))
        listFilter()
    }

    private fun listFilter() {
        val etSearchBarText = mBinding.etSearchBar.text
        adapter.submitList(contacts.filter {
            it.name.startsWith(etSearchBarText.toString(), true)
                    || it.name.contains(etSearchBarText.toString(), true)
        })
    }
}