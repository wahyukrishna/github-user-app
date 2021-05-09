package com.dicoding.favoriteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class UserItems(
    @PrimaryKey
    var id: Int = 0,
    var username: String? = null,
    var avatar: String? = null,
) : Serializable
