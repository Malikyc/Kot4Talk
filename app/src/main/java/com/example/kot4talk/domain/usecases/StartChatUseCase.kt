package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository

class StartChatUseCase (private val messengerRepository: MessengerRepository) {
    operator fun invoke(chat: Chat) {
        messengerRepository.startChat(chat)
    }
}