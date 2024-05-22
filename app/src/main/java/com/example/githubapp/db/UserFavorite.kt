package com.example.githubapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserFavorite(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
)