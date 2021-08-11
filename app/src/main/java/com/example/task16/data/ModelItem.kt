package com.example.task16.data


import com.google.gson.annotations.SerializedName

data class ModelItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("type")
    val type: String
)