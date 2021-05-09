package com.dicoding.mygithubuser.db

import android.app.Application
import com.dicoding.mygithubuser.model.UserItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FavoriteRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var favDao: FavoriteDao? = null

    init {
        val db = FavoriteDatabase.getDatabase(application)
        favDao = db?.favoriteDao()
    }

    fun getFavList() = favDao?.getFavoriteList()

    fun insertFav(fav: UserItems){
        launch {
            withContext(Dispatchers.IO){
                favDao?.insertFav(fav)
            }
        }
    }

    fun favCheck(fav: Int) = favDao?.favoriteCheck(fav)

    fun deleteFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFavorite(id)
        }
    }
}