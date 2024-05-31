package com.example.userinfocollector.domain.repository

import com.example.userinfocollector.data.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun getAllUsers(): List<User>
}