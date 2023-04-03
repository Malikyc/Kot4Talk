package com.example.kot4talk.domain.entities

data class Chat(
    val user1 : User = User(),
    val users2 : User = User(),
    val listOfMessage: MutableList<Message> = mutableListOf()
){
    val name:String
    get() = user1.email+"-"+users2.email
}