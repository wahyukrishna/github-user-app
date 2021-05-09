package com.dicoding.mygithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.BuildConfig
import com.dicoding.mygithubuser.model.UserItems
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setListUser(user: String?) {
        val url = "https://api.github.com/search/users?q=${user}"
        val list = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        val token = BuildConfig.apiKey
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                val items = responseObject.getJSONArray("items")
                try {
                    for (i in 0 until items.length()) {
                        val gson = Gson()
                        val userItems = gson.fromJson(items.getJSONObject(i).toString(), UserItems::class.java)
                        list.add(userItems)
                    }
                    listUser.postValue(list)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getListUser(): LiveData<ArrayList<UserItems>> {
        return listUser
    }

}