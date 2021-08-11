package com.example.task16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task16.data.ModelItem
import com.example.task16.util.GSON_PHONE_DIRECTORY_
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonList = GSON_PHONE_DIRECTORY_
        val gson = Gson()
        val listOfModels = object : TypeToken<List<ModelItem>>() {}.type
        val models: List<ModelItem> = gson.fromJson(jsonList, listOfModels)

        val listOfModelsForReading = models.forEachIndexed  { idx, tut -> println("> Item ${idx}:\n${tut}") }
        Timber.d(listOfModelsForReading.toString())
    }
}