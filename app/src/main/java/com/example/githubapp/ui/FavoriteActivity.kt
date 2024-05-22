package com.example.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.adapter.FavoriteAdapter
import com.example.githubapp.viewmodel.FavoriteViewModel
import com.example.githubapp.viewmodel.ViewModelFactory
import com.example.githubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteAdapter

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        adapter = FavoriteAdapter().apply {
            binding?.rvMyfav?.adapter = this
        }

        binding?.rvMyfav?.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this) { favoriteList ->
            if (favoriteList != null) {
                adapter.setFavorites(favoriteList)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}