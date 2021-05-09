package com.dicoding.mygithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.mygithubuser.model.UserItems
import com.dicoding.mygithubuser.db.FavoriteRepository

class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    private lateinit var  _favlist: LiveData<List<UserItems>>
    val favList get() = _favlist
    private val favRepository= FavoriteRepository(application)

    fun insertFav(fav: UserItems){
        favRepository.insertFav(fav)
    }

    fun getFav(){
        favRepository.getFavList()?.let {
            _favlist = it
        }
    }


    fun checkFavorite(fav: Int) =
        favRepository.favCheck(fav)


    fun deleteFav(fav: Int){
        favRepository.deleteFav(fav)
    }
}