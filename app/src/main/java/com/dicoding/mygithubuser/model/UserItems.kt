package com.dicoding.mygithubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "favorite")
data class UserItems(
    @PrimaryKey
    @field: SerializedName("id")
    var id: Int = 0,
    @field: SerializedName("login")
    var username: String? = null,
    @field: SerializedName("avatar_url")
    var avatar: String? = null,
    @field: SerializedName("name")
    var name: String? = null,
    @field: SerializedName("location")
    var location: String? = null,
    @field: SerializedName("company")
    var company: String? = null,
    @field: SerializedName("public_repos")
    var repo: Int = 0
) : Parcelable
