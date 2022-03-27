package com.example.submission2githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2githubuser.database.FavoriteUser
import com.example.submission2githubuser.databinding.ActivityDetailUserBinding
import com.example.submission2githubuser.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailUserActivity"
        const val USERNAME = "username"
        const val EXTRA_FAVORITE_USER = "extra_favorite_user"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private lateinit var activityDetailUSerBinding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var isFavorite = false
    private var favoriteUser: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailUSerBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(activityDetailUSerBinding.root)

        val username = intent.getStringExtra(USERNAME)
        displayUserDetail(username.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()

        activityDetailUSerBinding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(activityDetailUSerBinding.tabs, activityDetailUSerBinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f


        detailUserViewModel = obtainViewModel(this@DetailUserActivity)

//        val countUser = detailUserViewModel.countFavoriteUser(username.toString())
//        Log.d(TAG, "onCreate: $countUser")
//        if(countUser == 1) {
//            val checked: Int = R.drawable.ic_star_gold
//            activityDetailUSerBinding.btnFavorite.setImageResource(checked)
//        }

        activityDetailUSerBinding.btnFavorite.setOnClickListener {
            val checked: Int = R.drawable.ic_star_gold
            activityDetailUSerBinding.btnFavorite.setImageResource(checked)
//            startActivity(Intent(this, FavoriteUserActivity::class.java))
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun displayUserDetail(username: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: retrofit2.Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        setUsersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setUsersData(items: DetailUserResponse) {
        Glide.with(this)
            .load(items.avatarUrl)
            .circleCrop()
            .into(activityDetailUSerBinding.imgDetailPhoto)

        val textDetailName = items.name
        val textDetailUsername = items.login
        val textDetailCompany = "Company : ${items.company}"
        val textDetailLocation = "Location : ${items.location}"
        val textDetailRepos = "Public Repos : ${items.publicRepos}"

        activityDetailUSerBinding.tvDetailName.text = textDetailName
        activityDetailUSerBinding.tvDetailUsername.text = textDetailUsername
        activityDetailUSerBinding.tvDetailCompany.text = textDetailCompany
        activityDetailUSerBinding.tvDetailLocation.text = textDetailLocation
        activityDetailUSerBinding.tvDetailRepository.text = textDetailRepos
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            activityDetailUSerBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityDetailUSerBinding.progressBar.visibility = View.GONE
        }
    }
}