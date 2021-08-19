package com.example.task16

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task16.data.ContactItem

class Adapter : ListAdapter<ContactItem, ViewHolder>(UserItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,
                parent,
                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var name: TextView = view.findViewById(R.id.textName)
        private var phone: TextView = view.findViewById(R.id.textPhone)
        private var type: TextView = view.findViewById(R.id.textType)

        fun bind(item: ContactItem) {
            name.text = item.name
            phone.text = item.phone
            type.text = item.type
        }

}

object UserItemDiffCallback : DiffUtil.ItemCallback<ContactItem>() {
        override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem)
                : Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem)
                : Boolean = oldItem == newItem
    }
