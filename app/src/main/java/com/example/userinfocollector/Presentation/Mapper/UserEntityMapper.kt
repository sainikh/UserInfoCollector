package com.example.userinfocollector.Presentation.Mapper

import com.example.userinfocollector.Presentation.data.UserToDisplay
import com.example.userinfocollector.data.model.User
import com.example.userinfocollector.domain.model.UserUtils

class UserEntityMapper {
    fun mapUserToUserToDisplay(user: User): UserToDisplay {
        return UserToDisplay(
            name = user.name,
            age = user.age,
            dob = UserUtils.getFormattedDateFormLong(
                user.dobTimeStamp
            ),
            address = user.address
        )
    }
}