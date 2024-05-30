package com.example.userinfocollector.data

import androidx.lifecycle.LiveData
import com.example.userinfocollector.domain.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}