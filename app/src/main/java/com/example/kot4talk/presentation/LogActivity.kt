package com.example.kot4talk.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kot4talk.R
import com.example.kot4talk.databinding.ActivityLogBinding
import com.example.kot4talk.databinding.ActivityRegBinding
import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.presentation.viewModels.AuthViewModel

class LogActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLogBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[AuthViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeOnLiveData()
        onEditTextListenerAddition()
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        binding.loginButton.setOnClickListener {
            with(binding){
                val email  = etEmail.text.toString()
                val password = etPassword.text.toString()
                val user = User(email = email,password = password)
                viewModel.signIn(user)
            }

        }
    }


    private fun onEditTextListenerAddition() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetEmailError()
                viewModel.resetPasswordError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetPasswordError()
                viewModel.resetEmailError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun subscribeOnLiveData() {
        viewModel.emailIsInError.observe(this) {
            if (it == true) {
                binding.tilEmail.error = "Error"
            } else {
                binding.tilEmail.error = null
            }

        }
        viewModel.passwordIsInError.observe(this) {
            if (it == true) {
                binding.tilPassword.error = "Error"
            } else {
                binding.tilPassword.error = null
            }
        }
        viewModel.shouldBeClosed.observe(this) {
            if (it == true) {
                val intent = Intent(this@LogActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
