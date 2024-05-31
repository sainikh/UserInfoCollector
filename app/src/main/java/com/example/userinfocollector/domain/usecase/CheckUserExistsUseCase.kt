package com.example.userinfocollector.domain.usecase

import com.example.userinfocollector.domain.repository.UserRepository


class CheckUserExistsUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(username: String): Boolean {
        return if (!repository.getAllUsers().isNullOrEmpty()) {
            repository.getAllUsers()!!.any { it.name == username }
        } else
            false
    }

}