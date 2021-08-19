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
        initRecyclerView(adapter)
        adapter.submitList(contacts)
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
                    adapter.submitList(contacts.filter {
                        it.name.startsWith(etSearchBarText.toString(), true)
                                || it.name.contains(etSearchBarText.toString(), true)
                    })
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

    private fun initRecyclerView(adapter: Adapter) {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter
    }

    private fun saveState(list: String) {
        val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("SHARED_PREF",
                        MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("shared preferences", list)
        editor.apply()
    }

    private fun loadState() {
        val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val text = sharedPreferences.getString("shared preferences", null)
        mBinding.etSearchBar.setText(text)
        val etSearchBarText = mBinding.etSearchBar.text
        val contacts = parseContacts()
        adapter.submitList(contacts.filter {
            it.name.startsWith(etSearchBarText.toString(), true)
                    || it.name.contains(etSearchBarText.toString(), true)
        })
    }
}