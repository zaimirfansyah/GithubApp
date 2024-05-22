package com.example.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.repo.Repository
import com.example.githubapp.db.UserFavorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mRepository: Repository = Repository(application)

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mRepository.getAllFavorites()
}