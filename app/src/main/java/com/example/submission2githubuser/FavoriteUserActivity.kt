package com.example.submission2githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.databinding.ActivityFavoriteUserBinding
import com.example.submission2githubuser.helper.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var activityFavoriteUserBinding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(activityFavoriteUserBinding.root)

        title = getString(R.string.favorite_title)

        val favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavoriteUser().observe(this, {favoriteList ->
            if(favoriteList != null) {
                setFavoriteData(favoriteList)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        activityFavoriteUserBinding.recycleViewFav.layoutManager = layoutManager
    }

    private fun setFavoriteData(items: List<FavoriteUser>) {
        val listFavorite = ArrayList<User>()
        for(item in items) {
            val user = User(
                item.username,
                null,
                item.photo,
                null,
                null,
                null,
                null,
                null
            )
            listFavorite.add(user)
        }

        val adapter = FavoriteUserAdapter(listFavorite)
        activityFavoriteUserBinding.recycleViewFav.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }
}