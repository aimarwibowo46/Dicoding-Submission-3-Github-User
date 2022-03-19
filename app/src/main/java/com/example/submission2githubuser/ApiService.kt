package com.example.submission2githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") q: String,
        @Query("per_page") per_page: Int
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponse>>
}