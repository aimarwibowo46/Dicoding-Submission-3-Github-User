package com.example.submission2githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2githubuser.databinding.ActivityMainBinding
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        var USERNAME = "aimarwibowo46"
        const val PER_PAGE = 100
    }

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        title = getString(R.string.main_title)

        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager

        searchUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                USERNAME = query
                searchUsers()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteUserActivity::class.java))
                return true
            }
            R.id.change_theme -> {
                startActivity(Intent(this, ThemeActivity::class.java))
                return true
            }
            R.id.notification -> {
                startActivity(Intent(this, NotificationSettingsActivity::class.java))
                return true
            }
        }
        return true
    }

    private fun searchUsers() {
        showLoading(true)

        val client = ApiConfig.getApiService().getUsers(USERNAME, PER_PAGE)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: retrofit2.Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        setUsersData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<SearchUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setUsersData(items: List<ItemsItem>) {
        val listUsers = ArrayList<User>()
        for(item in items) {
            val user = User(
                item.login,
                null,
                item.avatarUrl,
                null,
                null,
                null,
                null,
                null
            )
            listUsers.add(user)
        }
        Log.d(TAG, "setUsersData: $listUsers")

        val adapter = SearchUsersAdapter(listUsers)
        activityMainBinding.rvUser.adapter = adapter
    }
}