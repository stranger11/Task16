package com.example.task16.util

import androidx.recyclerview.widget.DiffUtil
import com.example.task16.data.ContactItem

class ContactItemDiffUtil (val oldList: List<ContactItem>, val newList: List<ContactItem>)
    : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}