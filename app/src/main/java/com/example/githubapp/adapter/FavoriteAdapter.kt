package com.example.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.utils.DiffCallback
import com.example.githubapp.db.UserFavorite
import com.example.githubapp.databinding.ItemUserBinding
import com.example.githubapp.ui.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<UserFavorite>()

    fun setFavorites(favorites: List<UserFavorite>) {
        val diffCallback = DiffCallback(this.listFavorites, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding
        .root) {
        fun bind(favorites: UserFavorite) {
            with(binding) {
                usernameTextView.text = favorites.login
                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("username", favorites.login)
                    context.startActivity(intent)
                }
            }

            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .circleCrop()
                .into(binding.fotoprofil)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = listFavorites[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = listFavorites.size
}