package com.example.userinfocollector.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.userinfocollector.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
     suspend fun getAllUsers(): List<User>
}