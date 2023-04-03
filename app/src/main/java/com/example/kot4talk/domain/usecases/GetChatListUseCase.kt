package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository

class GetChatListUseCase (private val messengerRepository: MessengerRepository) {
    operator fun invoke(user: User) {
        messengerRepository.getChatList(user)
    }
}