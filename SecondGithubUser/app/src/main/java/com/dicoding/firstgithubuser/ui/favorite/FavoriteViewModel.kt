package com.dicoding.firstgithubuser.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.firstgithubuser.data.UserRepository
import com.dicoding.firstgithubuser.data.room.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UserRepository) : ViewModel() {

    fun addOrDeleteFavUser(user: UserEntity, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                repository.deleteFavoriteUser(user, false)
            } else {
                repository.addFavoriteUser(user, true)
            }
        }
    }

    fun deleteListFav(user: String) = repository.deleteUserFromList(user)
    fun getFavUser() = repository.getFavouriteUser()
}