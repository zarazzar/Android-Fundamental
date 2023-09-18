package com.dicoding.firstgithubuser.response

import com.google.gson.annotations.SerializedName

data class ResponseUsers(

	@field:SerializedName("items")
	val items: List<AllUsersItem>
)

data class AllUsersItem(
	//username
	@field:SerializedName("login")
	val login: String? = null,
	//avatar url
	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)

data class DetailUser(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	)
