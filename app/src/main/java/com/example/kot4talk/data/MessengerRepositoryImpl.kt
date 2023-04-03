package com.example.kot4talk.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.kot4talk.domain.entities.Chat
import com.example.kot4talk.domain.entities.Message
import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository
import com.example.kot4talk.presentation.RegActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MessengerRepositoryImpl(private val application: Application,
                              private val db : FirebaseFirestore,
private val auth : FirebaseAuth) : MessengerRepository {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun addUser(user: User) {
    auth.createUserWithEmailAndPassword(user.email,user.password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                db.collection(USER_COLL).document(user.email)
                    .set(user)
                    .addOnSuccessListener {
                        Log.i("ITHAPPENED","YEEH")
                    }
                    .addOnFailureListener{
                        Log.i("ITHAPPENED","Exception:$it")
                    }
            } else {
                Toast.makeText(application, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                Log.i("LETSSEE","${task.exception}")

            }
        }


    }

    override fun sendMessage(chat:Chat,message: Message) {
        db.collection(CHAT_COLL)
            .document(chat.name)
            .update("listOfMessage",FieldValue.arrayUnion(message))
    }

    override fun startChat(chat: Chat) {
        var chat1 : Chat? = null
     db.collection(CHAT_COLL).document(chat.name).get().addOnSuccessListener {
                 chat1 = it.toObject<Chat>()
         if(chat1 == null) {
             db.collection(CHAT_COLL)
                 .document(chat.name)
                 .set(chat)
         }
     }


    }

    override  fun getUserList(): MutableSharedFlow<List<User>> {
        val flow = MutableSharedFlow<List<User>>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            val listOfUsers = mutableListOf<User>()
            db.collection(USER_COLL).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.i("LetMeSee", e.message.toString())
                }
                if (snapshot == null) return@addSnapshotListener
                listOfUsers.clear()
                for (qDoc in snapshot) {
                    listOfUsers.add(qDoc.toObject())
                }
                scope.launch {
              flow.emit(listOfUsers)}
        }
        return flow
    }

    override fun getChatList(user: User): MutableSharedFlow<List<Chat>> {
        val flow = MutableSharedFlow<List<Chat>>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        val listOfChats = mutableListOf<Chat>()
        db.collection(CHAT_COLL).addSnapshotListener{
            snapshot,e->
            if (e != null){
                Log.i("LetMeSee",e.message.toString())
            }
            if (snapshot == null) return@addSnapshotListener
            listOfChats.clear()
            for (qDoc in snapshot){
                val chat = qDoc.toObject<Chat>()
                if(chat.name.contains(user.email)){
                    listOfChats.add(chat)
                }
            }
            scope.launch {
                flow.emit(listOfChats)
            }
        }
        return flow
    }

    override fun getMessageList(chat : Chat): MutableSharedFlow<List<Message>> {
        val flow = MutableSharedFlow<List<Message>>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        var listOfMessage = mutableListOf<Message>()
        db.collection(CHAT_COLL).addSnapshotListener{
                snapshot,e->
            if (e != null){
                Log.i("LetMeSee",e.message.toString())
            }
            if (snapshot == null) return@addSnapshotListener
            for (qDoc in snapshot) {
                val chatName = qDoc.id
                if (chatName == chat.name) {
                    val chatQ = qDoc.toObject<Chat>()
                    listOfMessage = chatQ.listOfMessage
                }
            }
            scope.launch {
                flow.emit(listOfMessage)
            }
        }
    return flow
    }

    override fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    override fun signIn(user: User) {
        auth.signInWithEmailAndPassword(user.email,user.password)
    }

    override fun signOut() {
        auth.signOut()
    }

    companion object{
        private const val USER_COLL = "users"
        private const val CHAT_COLL = "chats"
    }
}