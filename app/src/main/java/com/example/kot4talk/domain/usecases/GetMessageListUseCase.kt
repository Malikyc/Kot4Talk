package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.repository.MessengerRepository

class GetMessageListUseCase (private val messengerRepository: MessengerRepository) {
    operator fun invoke(chat: Chat) {
        messengerRepository.getMessageList(chat)
    }
}