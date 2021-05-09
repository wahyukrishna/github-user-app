package com.dicoding.favoriteapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.mygithubuser"
    const val SCHEME = "content"

    class FavColumns : BaseColumns {

        companion object {
            private const val TABLE_NAME = "favorite"
            const val ID = "id"
            const val AVATAR = "avatar"
            const val USERNAME = "username"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}