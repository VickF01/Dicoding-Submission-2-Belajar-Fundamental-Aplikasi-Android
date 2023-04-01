package com.example.githubuser.data

import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.data.local.room.UserDao
import com.example.githubuser.data.remote.retrofit.ApiService
import com.example.githubuser.utils.AppExecutors

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {
    fun getAllFavorite(): LiveData<List<UserEntity>> {
        return userDao.getAllFavorite()
    }

    fun getFavoriteUser(login: String): LiveData<List<UserEntity>> {
        return userDao.getFavoriteUser(login)
    }

    fun insert(user: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.insert(user)
        }
    }

    fun delete(user: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.delete(user)
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}