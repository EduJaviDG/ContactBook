package com.example.contact_book.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_book.databinding.ItemRowBinding
import com.example.contact_book.data.database.user.User

class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding = ItemRowBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun render(itemUser: User, onClickListener:(user: User)->Unit){

        binding.tvIdUser.text = itemUser.id.toString()

        binding.tvFirstName.text = itemUser.firstname

        binding.tvLastName.text = itemUser.lastname

        binding.tvPhone.text = itemUser.phone.toString()

        binding.tvEnterprise.text = itemUser.enterprise.name

        binding.tvEmail.text = itemUser.enterprise.email

        binding.ivCircleImage.setImageBitmap(itemUser.profilePhoto)

        itemView.setOnClickListener {

            onClickListener(itemUser)
        }

    }

}