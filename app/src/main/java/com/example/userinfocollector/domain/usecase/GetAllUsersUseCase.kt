package com.example.userinfocollector.domain.usecase

import com.example.userinfocollector.Presentation.Mapper.UserEntityMapper
import com.example.userinfocollector.Presentation.data.UserToDisplay
import com.example.userinfocollector.domain.repository.UserRepository

class GetAllUsersUseCase (private val repository: UserRepository,private val userEntityMapper: UserEntityMapper) {
    suspend operator fun invoke(): List<UserToDisplay> {
       return repository.getAllUsers().map {
            userEntityMapper.mapUserToUserToDisplay(it)
        }
    }
}