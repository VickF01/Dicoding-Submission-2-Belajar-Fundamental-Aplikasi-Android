package com.example.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    var login: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,
) : Parcelable