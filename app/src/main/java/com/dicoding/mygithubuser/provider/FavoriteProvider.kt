package com.dicoding.mygithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.mygithubuser.db.DatabaseContract.AUTHORITY
import com.dicoding.mygithubuser.db.DatabaseContract.FavColumns.Companion.TABLE_NAME

import com.dicoding.mygithubuser.db.FavoriteDao
import com.dicoding.mygithubuser.db.FavoriteDatabase

class FavoriteProvider : ContentProvider() {
    private lateinit var favDao: FavoriteDao

    companion object{
        private const val ID_FAV = 1
        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init{
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAV)
        }

    }

    override fun onCreate(): Boolean {
        favDao = context?.let { context ->
            FavoriteDatabase.getDatabase(context)?.favoriteDao()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            ID_FAV -> {
                cursor = favDao.getFavorite()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {cursor = null
        }
    }
        return  cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

}