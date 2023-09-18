package com.dicoding.firstgithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.firstgithubuser.response.AllUsersItem
import com.dicoding.firstgithubuser.response.ResponseUsers
import com.dicoding.firstgithubuser.retrofit.ApiConfig
import com.dicoding.firstgithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snacbarText: LiveData<Event<String>> = _snackBarText

    private val _dataUser = MutableLiveData<List<AllUsersItem>?>()
    val dataUser: LiveData<List<AllUsersItem>?> = _dataUser

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        showAllUser()
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(
                call: Call<ResponseUsers>,
                response: Response<ResponseUsers>
            ) {
                _isLoading.value = false
                val dataUser = response.body()
                if (response.isSuccessful) {
                    _dataUser.value = dataUser?.items
                    if (dataUser?.items.isNullOrEmpty()) {
                        _snackBarText.value = Event("User not exists T_T")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()} ")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event("Fetching Data Failed (T_T)")
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun showAllUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUsers()
        client.enqueue(object : Callback<List<AllUsersItem>> {
            override fun onResponse(
                call: Call<List<AllUsersItem>>,
                response: Response<List<AllUsersItem>>
            ) {
                _isLoading.value = false
                val dataUser = response.body()
                if (response.isSuccessful)
                    _dataUser.value = dataUser
            }

            override fun onFailure(call: Call<List<AllUsersItem>>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event("Fetching Data Failed (T_T)")
            }

        })
    }

}