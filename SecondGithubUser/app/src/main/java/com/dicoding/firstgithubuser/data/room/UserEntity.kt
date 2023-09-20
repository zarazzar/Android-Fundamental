package com.dicoding.firstgithubuser.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "TableUsers")
@Parcelize
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatar: String?,

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean? = null
) : Parcelable