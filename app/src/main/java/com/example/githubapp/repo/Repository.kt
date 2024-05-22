package com.example.githubapp.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp.db.UserFavorite
import com.example.githubapp.db.UserFavoriteDao
import com.example.githubapp.db.UserFavoriteRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val mUserFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteRoomDB.getDatabase(application)
        mUserFavoriteDao = db.userFavoriteDao()
    }

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mUserFavoriteDao.getAll()

    fun insert(user: UserFavorite) {
        executorService.execute { mUserFavoriteDao.insertToFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mUserFavoriteDao.removeFromFavorite(id) }
    }
}