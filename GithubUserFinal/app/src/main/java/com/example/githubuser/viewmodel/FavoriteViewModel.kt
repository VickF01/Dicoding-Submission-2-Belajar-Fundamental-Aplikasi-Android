package com.example.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.local.entity.UserEntity

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getAllFavorite() = userRepository.getAllFavorite()

    fun getFavoriteUser(login: String) = userRepository.getFavoriteUser(login)

    fun insert(user: UserEntity) {
        userRepository.insert(user)
    }

    fun delete(user: UserEntity) {
        userRepository.delete(user)
    }

}