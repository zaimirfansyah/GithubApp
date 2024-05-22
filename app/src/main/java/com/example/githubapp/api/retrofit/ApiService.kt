package com.example.githubapp.api.retrofit

import com.example.githubapp.api.response.DetailUserResponse
import com.example.githubapp.api.response.GithubResponse
import com.example.githubapp.api.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getGithubUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("search/users")
    fun searchGithubUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}


