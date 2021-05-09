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
import org.json.JSONArray
import java.lang.Exception

class FollowerViewModel : ViewModel() {
    companion object {
        private val TAG = FollowerViewModel::class.java.simpleName
    }

    val listFollower = MutableLiveData<ArrayList<UserItems>>()
    fun setListFollower(follower: String?) {
        val list = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${follower}/followers"
        val token = BuildConfig.apiKey
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")
        client.get(url,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)
                    Log.d(TAG, result)
                    try {
                        for (i in 0 until responseObject.length()) {
                            val gson = Gson()
                            val userItems = gson.fromJson(responseObject.getJSONObject(i).toString(), UserItems::class.java)
                            list.add(userItems)
                        }
                        listFollower.postValue(list)
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

    fun getListFollower(): LiveData<ArrayList<UserItems>> {
        return listFollower
    }
}