package com.dicoding.mygithubuser.db

import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.mygithubuser"

    class FavColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
        }

    }
}