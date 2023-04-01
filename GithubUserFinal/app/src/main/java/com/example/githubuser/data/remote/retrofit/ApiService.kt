package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.remote.response.GithubResponse
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("search/users")
    fun getUsername(
        @Query("q") q: String
    ) : Call<GithubResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ) : Call<UserDetailResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<ItemsItem>>
}