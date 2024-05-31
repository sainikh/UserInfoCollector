package com.example.userinfocollector.domain.usecase


import com.example.userinfocollector.data.model.User
import com.example.userinfocollector.domain.repository.UserRepository

class SaveUserUseCase(private val repository: UserRepository) {
    suspend fun execute(user: User) = repository.insert(user = user)
}