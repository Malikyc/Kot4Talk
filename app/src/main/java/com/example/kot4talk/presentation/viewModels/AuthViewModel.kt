package com.example.kot4talk.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot4talk.data.MessengerRepositoryImpl
import com.example.kot4talk.domain.entities.User
import com.example.kot4talk.domain.repository.MessengerRepository
import com.example.kot4talk.domain.usecases.AddUserUseCase
import com.example.kot4talk.domain.usecases.SignInUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AuthViewModel( application: Application) : AndroidViewModel(application) {
    private val repository = MessengerRepositoryImpl(application,
        Firebase.firestore,
        Firebase.auth)
    private val addUserUseCase: AddUserUseCase = AddUserUseCase(repository)
    private val signInUseCase: SignInUseCase = SignInUseCase(repository)

      private var _emailIsInError = MutableLiveData<Boolean>()
      val emailIsInError = _emailIsInError

      private var _passwordIsInError = MutableLiveData<Boolean>()
      val passwordIsInError = _passwordIsInError

      private var _shouldBeClosed = MutableLiveData<Boolean>()
      val shouldBeClosed = _shouldBeClosed

    fun registerUser(user: User){
        viewModelScope.launch {
            validateInput(user)
            if(emailIsInError.value != true && passwordIsInError.value != true){
                addUserUseCase.invoke(user).collect{
                    if(it is RuntimeException){
                        registered()
                    }
                    _emailIsInError.value = true
                }
            }
         }
      }

    fun signIn(user: User){
        viewModelScope.launch {
        validateInput(user)
            if(emailIsInError.value != true && passwordIsInError.value != true){
        signInUseCase.invoke(user).collect{
            if(it is RuntimeException){
                registered()
            }
            _emailIsInError.value = true
        }
            }
        }
    }


    fun resetEmailError(){
        _emailIsInError.value = false
    }

    fun resetPasswordError(){
        _passwordIsInError.value = false
    }

   private fun registered(){
        _shouldBeClosed.value = true
    }

    private fun validateInput(user : User){
        val userVM = User(email = user.email.trim(),
            password = user.password.trim(),
            imageUrl = user.imageUrl.trim())
        val email = userVM.email
        val password = userVM.password
        if(email.isEmpty()){
            _emailIsInError.value = true
        }
        if(password.isEmpty() || password.length < 6){
            _passwordIsInError.value = true
        }
    }
}