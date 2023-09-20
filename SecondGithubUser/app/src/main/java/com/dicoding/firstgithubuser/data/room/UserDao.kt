package com.dicoding.firstgithubuser.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM TableUsers where favorite = 1")
    fun getFavoritedUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM TableUsers where login = :username")
    fun deleteList(username: String)

}