package com.example.submission2githubuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository =  FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> {
        return mFavoriteUserRepository.getAllFavoriteUser()
    }
}