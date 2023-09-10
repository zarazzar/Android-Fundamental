package com.dicoding.firstgithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.firstgithubuser.response.DetailUser
import com.dicoding.firstgithubuser.retrofit.ApiConfig
import com.dicoding.firstgithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _theDetails = MutableLiveData<DetailUser>()
    val theDetails: LiveData<DetailUser> = _theDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackBar

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetails(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _theDetails.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()} ")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                _snackBar.value = Event("Fetching user detail failed (T_T) ")
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}