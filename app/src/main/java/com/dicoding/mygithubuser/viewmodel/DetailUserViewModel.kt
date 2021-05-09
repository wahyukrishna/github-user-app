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

class DetailUserViewModel : ViewModel() {
    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

    val userDetail = MutableLiveData<UserItems>()
    fun setUserDetail(user: String?) {
        val url = "https://api.github.com/users/${user}"
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
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val gson = Gson()
                    val userItems = gson.fromJson(responseObject.toString(), UserItems::class.java)
                    userDetail.postValue(userItems)
                } catch (e: Exception) {
                    e.printStackTrace()
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
    fun getUserDetail(): LiveData<UserItems> {
        return userDetail
    }
}