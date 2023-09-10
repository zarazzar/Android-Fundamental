package com.dicoding.firstgithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.firstgithubuser.response.AllUsersItem
import com.dicoding.firstgithubuser.retrofit.ApiConfig
import com.dicoding.firstgithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isZero = MutableLiveData<Boolean>()
    val isZero: LiveData<Boolean> = _isZero

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackBar

    private val _listUserFollow = MutableLiveData<List<AllUsersItem>>()
    val listUserFollow: LiveData<List<AllUsersItem>> = _listUserFollow

    companion object {
        private const val TAG = "TabsViewModel"
    }

    fun getFollow(username: String, followingFirst: Boolean) {
        _isLoading.value = true
        val client = if (followingFirst) {
            ApiConfig.getApiService().getFollowing(username)
        } else {
            ApiConfig.getApiService().getFollowers(username)
        }
        client.enqueue(object : Callback<List<AllUsersItem>> {
            override fun onResponse(
                call: Call<List<AllUsersItem>>,
                response: Response<List<AllUsersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUserFollow.value = response.body()
                    if (response.body().isNullOrEmpty()) {
                        _isZero.value = true
                    }
                } else {
                    _snackBar.value = Event(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<AllUsersItem>>, t: Throwable) {
                _isLoading.value = false
                _snackBar.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

}