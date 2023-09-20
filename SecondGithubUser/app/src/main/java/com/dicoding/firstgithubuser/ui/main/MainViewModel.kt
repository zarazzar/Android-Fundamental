package com.dicoding.firstgithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.firstgithubuser.data.UserRepository
import com.dicoding.firstgithubuser.data.response.AllUsersItem
import com.dicoding.firstgithubuser.helper.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val dataUser: LiveData<List<AllUsersItem>?> = repository.dataUser
    val isLoading: LiveData<Boolean> = repository.isLoading
    val snacbarText: LiveData<Event<String>> = repository.snacbarText

    init {
        showAllUser()
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            repository.searchUser(query)
        }
    }

    fun showAllUser() {
        viewModelScope.launch {
            repository.showAllUser()
        }
    }

    fun getmode(): LiveData<Boolean> = repository.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

}