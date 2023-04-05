package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetUserListUseCase (private val messengerRepository: MessengerRepository) {
    suspend operator fun invoke() : Flow<List<User>> =
        messengerRepository.getUserList()

}