package com.dicoding.firstgithubuser.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.firstgithubuser.data.SettingPreferences
import com.dicoding.firstgithubuser.data.UserRepository
import com.dicoding.firstgithubuser.data.retrofit.ApiConfig
import com.dicoding.firstgithubuser.data.room.UserRoomDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preferences = SettingPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val dataBase = UserRoomDatabase.getInstance(context)
        val dao = dataBase.userDao()
        return UserRepository.getInstance(preferences, apiService, dao)
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("change mode")