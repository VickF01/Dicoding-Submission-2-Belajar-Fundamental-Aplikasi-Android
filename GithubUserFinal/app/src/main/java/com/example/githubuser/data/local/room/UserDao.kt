package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE login = :login")
    fun getFavoriteUser(login: String): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}