package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.repository.MessengerRepository

class SignOutUseCase (private val messengerRepository: MessengerRepository){
    operator fun invoke(){
        messengerRepository.signOut()
    }
}