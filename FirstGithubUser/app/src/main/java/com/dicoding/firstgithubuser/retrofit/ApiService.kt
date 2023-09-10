package com.dicoding.firstgithubuser.retrofit

import com.dicoding.firstgithubuser.response.AllUsersItem
import com.dicoding.firstgithubuser.response.DetailUser
import com.dicoding.firstgithubuser.response.ResponseUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getAllUsers(): Call<List<AllUsersItem>>

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<ResponseUsers>

    @GET("users/{username}")
    fun getDetails(
        @Path("username") username : String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowers (
        @Path("username") username : String
    ): Call<List<AllUsersItem>>
    @GET("users/{username}/following")
    fun getFollowing (
        @Path("username") username : String
    ): Call<List<AllUsersItem>>
}