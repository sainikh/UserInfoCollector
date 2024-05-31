package com.example.userinfocollector.data.local.repository

import com.example.userinfocollector.data.model.User
import com.example.userinfocollector.domain.UserDao
import com.example.userinfocollector.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}