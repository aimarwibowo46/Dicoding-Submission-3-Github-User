package com.example.submission2githubuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.repository.FavoriteUserRepository

class DetailUserViewModel(application: Application): ViewModel(){
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun update(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.update(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun countFavoriteUser(username: String): Int {
        return mFavoriteUserRepository.countFavoriteUser(username)
    }
}