package com.dicoding.mygithubuser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.mygithubuser.model.UserItems

@Database(version = 1, entities = [UserItems::class], exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object{

        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase? {
            if(INSTANCE == null){
                synchronized(FavoriteDatabase::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteDatabase::class.java, "favorite_database"
                        )
                            .build()
                    }
                }
            }
            return  INSTANCE
        }
    }

}