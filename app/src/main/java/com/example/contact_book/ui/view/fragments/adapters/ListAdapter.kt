package com.example.contact_book.ui.view.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_book.R
import com.example.contact_book.data.database.user.User

class ListAdapter(private val userList: List<User>, private val onClickListener:(user: User)->Unit): RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        return ListViewHolder(layoutInflater.inflate(R.layout.item_row,parent,false))

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.render(userList[position],onClickListener)


    }

    override fun getItemCount(): Int {

       return userList.size

    }


}