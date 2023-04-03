package com.example.kot4talk.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kot4talk.R
import com.example.kot4talk.data.MessengerRepositoryImpl
import com.example.kot4talk.databinding.ActivityRegBinding
import com.example.kot4talk.domain.entities.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RegActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegBinding.inflate(layoutInflater)
    }
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val rep = MessengerRepositoryImpl(application, Firebase.firestore, Firebase.auth)
        auth.signInWithEmailAndPassword("mil@mail.ru","hel456")
        lifecycleScope.launch {
            rep.getUserList().collect {
                Log.i("LETSSEE", it.toString())
            }
        }
        binding.registrationbButton.setOnClickListener{
            val user = User("mil556@mail.ru","//","hel456")
            rep.addUser(user)
        }
    }
}
