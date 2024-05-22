package com.example.githubapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.githubapp.db.UserFavorite

class DiffCallback(private val mOldFavoriteList: List<UserFavorite>, private val mNewFavoriteList: List<UserFavorite>):

    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteList = mOldFavoriteList[oldItemPosition]
        val newFavoriteList = mNewFavoriteList[newItemPosition]
        return oldFavoriteList.login == newFavoriteList.login && oldFavoriteList.avatarUrl == newFavoriteList.avatarUrl
    }
}