package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.entities.Message
import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository

class SendMessageUseCase (private val messengerRepository: MessengerRepository) {
    operator fun invoke(chat: Chat, message: Message) {
        messengerRepository.sendMessage(chat,message)
    }
}