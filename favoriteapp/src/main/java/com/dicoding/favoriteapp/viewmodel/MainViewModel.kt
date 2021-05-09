package com.dicoding.favoriteapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.favoriteapp.adapter.MappingHelper
import com.dicoding.favoriteapp.db.DatabaseContract
import com.dicoding.favoriteapp.model.UserItems

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setListUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listMapped = MappingHelper.mapCursorToArrayList(cursor)
        listUser.postValue(listMapped)
    }

    fun getListUser(): LiveData<ArrayList<UserItems>> {
        return listUser
    }
}