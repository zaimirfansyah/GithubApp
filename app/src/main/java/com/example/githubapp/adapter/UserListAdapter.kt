package com.example.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.api.response.ItemsItem
import com.example.githubapp.databinding.ItemUserBinding
import com.example.githubapp.ui.DetailActivity

class UserListAdapter(private var userList: List<ItemsItem>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = userList[position]
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra("username", user.login)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(user: ItemsItem) {
            binding.apply {
                usernameTextView.text = user.login
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .into(binding.fotoprofil)

            }
        }
    }

    fun submitList(newList: List<ItemsItem>) {
        userList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

