package com.example.submission2githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.database.FavoriteUserDao
import com.example.submission2githubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> {
        return mFavoriteUserDao.getAllFavoriteUser()
    }

    fun countFavoriteUser(username: String): Int {
        return mFavoriteUserDao.countFavoriteUser(username)
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteUserDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteUserDao.delete(favoriteUser)}
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteUserDao.update(favoriteUser)}
    }
}