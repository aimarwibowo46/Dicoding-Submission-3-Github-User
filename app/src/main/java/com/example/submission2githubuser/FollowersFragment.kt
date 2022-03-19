package com.example.submission2githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class FollowersFragment : Fragment() {

    companion object {
        const val TAG = "FollowersFragment"
        const val USERNAME = "aimarwibowo46"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowers: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        progressBar = view.findViewById(R.id.progressBarFollowers)
        rvFollowers = view.findViewById(R.id.recycleViewFollowers)

        displayUserFollowers(username.toString())
    }

    private fun displayUserFollowers(username: String) {
        showLoading(true)
        Log.d(TAG, "displayUserFollowers: TEST")

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowersResponse>> {
            override fun onResponse(
                call: retrofit2.Call<List<FollowersResponse>>,
                response: Response<List<FollowersResponse>>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d(TAG, "onResponse: ${responseBody.toString()}")
                    if(responseBody != null) {
                        setFollowersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<FollowersResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
            }

        })
    }

    private fun setFollowersData(items: List<FollowersResponse>) {
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
        Log.d(TAG, "setFollowersData: $listFollowers")

        rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FollowersAndFollowingAdapter(listFollowers)
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