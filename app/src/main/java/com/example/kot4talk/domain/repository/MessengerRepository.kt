package com.example.kot4talk.domain.repository


import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.entities.Message
import com.example.kot4talk.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface MessengerRepository {
    fun addUser(user: User)

    fun sendMessage(chat: Chat,message: Message)

    fun startChat(chat: Chat)

     fun getUserList() : Flow<List<User>>

   fun getChatList(user: User) : Flow<List<Chat>>

    fun getMessageList(chat: Chat) : Flow<List<Message>>

    fun isLoggedIn() : Boolean

    fun signIn(user: User)

    fun signOut()
}