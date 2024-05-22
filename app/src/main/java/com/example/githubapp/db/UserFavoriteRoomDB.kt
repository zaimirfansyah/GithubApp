package com.example.githubapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteRoomDB : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteRoomDB? = null

        @JvmStatic
        fun getDatabase(context: Context): UserFavoriteRoomDB {
            if (INSTANCE == null) {
                synchronized(UserFavoriteRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavoriteRoomDB::class.java, "favorite_user_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UserFavoriteRoomDB
        }
    }
}