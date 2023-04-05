package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.repository.MessengerRepository

class GetCurrentUserUseCase(private val messengerRepository: MessengerRepository) {
    operator fun invoke(email : String) = messengerRepository.getCurrentUser(email)
}