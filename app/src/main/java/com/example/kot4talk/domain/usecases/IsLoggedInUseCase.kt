package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.repository.MessengerRepository

class IsLoggedInUseCase (private val messengerRepository: MessengerRepository){
    operator fun invoke() =
        messengerRepository.isLoggedIn()

}