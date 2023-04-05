package com.example.kot4talk.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kot4talk.R
import com.example.kot4talk.data.MessengerRepositoryImpl
import com.example.kot4talk.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        }

    companion object{
      private  const val EXTRA_EMAIL = "email"

        fun newIntent(context : Context, email : String) : Intent{
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_EMAIL,email)
            }
        }
    }
}

