package com.example.kot4talk.domain.usecases

import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.entities.Message
import com.example.kot4talk.domain.repository.MessengerRepository
import kotlinx.coroutines.flow.Flow

class GetMessageListUseCase (private val messengerRepository: MessengerRepository) {
    operator fun invoke(chat: Chat) : Flow<List<Message>> =
        messengerRepository.getMessageList(chat)

}