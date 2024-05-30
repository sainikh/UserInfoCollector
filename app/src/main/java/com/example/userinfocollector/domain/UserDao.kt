package com.example.userinfocollector.domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.userinfocollector.data.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
     suspend fun getAllUsers(): List<User>
}