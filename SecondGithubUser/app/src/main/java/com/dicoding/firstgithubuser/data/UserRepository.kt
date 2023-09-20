package com.dicoding.firstgithubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.dicoding.firstgithubuser.data.response.AllUsersItem
import com.dicoding.firstgithubuser.data.response.ResponseUsers
import com.dicoding.firstgithubuser.data.retrofit.ApiService
import com.dicoding.firstgithubuser.data.room.UserDao
import com.dicoding.firstgithubuser.data.room.UserEntity
import com.dicoding.firstgithubuser.helper.Event
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository private constructor(
    private val preferences: SettingPreferences,
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snacbarText: LiveData<Event<String>> = _snackBarText

    private val _dataUser = MutableLiveData<List<AllUsersItem>?>()
    val dataUser: LiveData<List<AllUsersItem>?> = _dataUser

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = apiService.searchUser(query)
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
        val client = apiService.getAllUsers()
        client.enqueue(object : Callback<List<AllUsersItem>> {
            override fun onResponse(
                call: Call<List<AllUsersItem>>,
                response: Response<List<AllUsersItem>>
            ) {
                _isLoading.value = false
                val dataUser = response.body()
                if (response.isSuccessful)
                    _dataUser.value = dataUser!!
            }

            override fun onFailure(call: Call<List<AllUsersItem>>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event("Fetching Data Failed (T_T)")
            }

        })
    }

    suspend fun addFavoriteUser(user: UserEntity, favoriteBoolean: Boolean) {
        user.isFavorite = favoriteBoolean
        userDao.insertUser(user)
    }

    suspend fun deleteFavoriteUser(user: UserEntity, favoriteBoolean: Boolean) {
        user.isFavorite = favoriteBoolean
        userDao.deleteUser(user)
    }

    fun deleteUserFromList(user: String){
        executorService.execute { userDao.deleteList(user) }
    }

    fun getFavouriteUser(): LiveData<List<UserEntity>> {
        return userDao.getFavoritedUsers().asLiveData()
    }

    fun getThemeSetting(): Flow<Boolean> = preferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            preferences: SettingPreferences,
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(preferences, apiService, userDao)
        }.also { instance = it }

        private const val TAG = "UserRepository"
    }
}