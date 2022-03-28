package com.example.submission2githubuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.repository.FavoriteUserRepository

class DetailUserViewModel(application: Application): ViewModel(){
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
        Log.d("RANDOM", "insert: $favoriteUser")
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun countFavoriteUser(username: String): LiveData<List<FavoriteUser>> {
        Log.d("RANDOM2", "countFavoriteUser: ${mFavoriteUserRepository.countFavoriteUser(username).value}")
        return mFavoriteUserRepository.countFavoriteUser(username)
    }
}