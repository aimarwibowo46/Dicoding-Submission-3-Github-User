package com.example.submission2githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {

    companion object {
        const val TAG = "FollowersFragment"
        const val USERNAME = "aimarwibowo46"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowers: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBarFollowers)
        rvFollowers = view.findViewById(R.id.recycleViewFollowers)

        displayUserFollowers()
    }

    private fun displayUserFollowers() {
        showLoading(true)

        val client = ApiConfig.getApiService().getUserFollowers(USERNAME)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: retrofit2.Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        setFollowersData(responseBody.followersResponse)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<FollowersResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setFollowersData(items: List<FollowersResponseItem>) {
        val listFollowers = ArrayList<User>()
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
            listFollowers.add(user)
        }
        val adapter = FollowersAdapter(listFollowers)
        rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}