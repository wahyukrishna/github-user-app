package com.dicoding.mygithubuser.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.mygithubuser.model.UserItems

@Dao
interface FavoriteDao {
    @Insert
    fun insertFav(fav: UserItems)

    @Query("SELECT * FROM favorite")
    fun getFavoriteList(): LiveData<List<UserItems>>

    @Query("SELECT * FROM favorite WHERE id =:id")
    fun getFavoriteById(id: Int): Int

    @Query("SELECT count(*) FROM favorite WHERE id =:id")
    fun favoriteCheck(id: Int):Int

    @Query("DELETE FROM favorite WHERE id =:id")
    fun deleteFavorite(id: Int): Int

    @Query("SELECT * FROM favorite")
    fun getFavorite(): Cursor

}