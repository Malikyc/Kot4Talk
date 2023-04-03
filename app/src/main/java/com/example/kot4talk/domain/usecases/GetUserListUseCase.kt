package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.repository.MessengerRepository

class GetUserListUseCase (private val messengerRepository: MessengerRepository) {
    suspend operator fun invoke() {
        messengerRepository.getUserList()
    }
}