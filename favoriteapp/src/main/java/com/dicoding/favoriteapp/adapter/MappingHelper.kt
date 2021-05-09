package com.dicoding.favoriteapp.adapter

import android.database.Cursor
import com.dicoding.favoriteapp.db.DatabaseContract
import com.dicoding.favoriteapp.model.UserItems

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<UserItems>{
        val favList = ArrayList<UserItems>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME))
                val avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.AVATAR))

                favList.add(
                    UserItems(
                        id,
                        username,
                        avatar
                    )
                )
            }
        }
        return favList
    }
}