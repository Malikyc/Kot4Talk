package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository
import kotlinx.coroutines.flow.Flow

class AddUserUseCase(private val messengerRepository: MessengerRepository)  {
    operator fun invoke(user: User) : Flow<Exception> =
        messengerRepository.addUser(user)

}