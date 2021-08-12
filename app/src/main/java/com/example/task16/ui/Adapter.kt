package com.example.task16

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task16.data.ContactItem

class Adapter(var phoneItems: List<ContactItem>, ) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount() = phoneItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val images = phoneItems[position]
        holder.bind(images)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var name: TextView = view.findViewById(R.id.textName)
    private var phone: TextView = view.findViewById(R.id.textPhone)
    private var type: TextView = view.findViewById(R.id.textType)

    fun bind(item: ContactItem){
        name.text = item.name
        phone.text = item.phone
        type.text = item.type
    }
}