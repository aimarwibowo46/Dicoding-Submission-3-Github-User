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

class FollowingFragment : Fragment() {

    companion object {
        const val TAG = "FollowingFragment"
        const val USERNAME = "aimarwibowo46"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowing: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        progressBar = view.findViewById(R.id.progressBarFollowing)
        rvFollowing = view.findViewById(R.id.recycleViewFollowing)

        displayUserFollowing(username.toString())
    }

    private fun displayUserFollowing(username: String) {
        showLoading(true)
        Log.d(TAG, "displayUserFollowing: TEST")

        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: retrofit2.Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
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

            override fun onFailure(call: retrofit2.Call<List<FollowingResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
            }

        })
    }

    private fun setFollowersData(items: List<FollowingResponse>) {
        val listFollowing = ArrayList<User>()
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
            listFollowing.add(user)
        }
        Log.d(TAG, "setFollowingData: $listFollowing")

        rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FollowersAndFollowingAdapter(listFollowing)
        rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}