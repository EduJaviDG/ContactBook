package com.example.contact_book.viewmodel

import androidx.lifecycle.*
import com.example.contact.domain.usecase.usecaseAuth.UseCaseSignUp
import com.example.contact_book.data.resources.Resource
import com.example.contact_book.domain.usecase.usecaseAuth.UseCaseActive
import com.example.contact_book.domain.usecase.usecaseAuth.UseCaseLogin
import com.example.contact_book.domain.usecase.usecaseAuth.UseCaseLogout
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val caseLogin: UseCaseLogin, private val caseSignUp: UseCaseSignUp,
    private val caseLogout: UseCaseLogout, private val caseActive: UseCaseActive
) : ViewModel() {

    private var _login = MutableLiveData<Resource<FirebaseUser>?>(null)

    val login:LiveData<Resource<FirebaseUser>?> = _login

    private var _signUp = MutableLiveData<Resource<FirebaseUser>?>(null)

    val signUp:LiveData<Resource<FirebaseUser>?> = _signUp


    val currentUser: FirebaseUser?
        get() = caseActive.currentUserCase


     fun login(email: String, password: String) = viewModelScope.launch {

       _login.value = Resource.inProgress

       val result = caseLogin.loginCase(email, password)

       _login.value = result

    }


    fun signUp(name: String, email: String, password: String) = viewModelScope.launch {

        _signUp.value = Resource.inProgress

        val result = caseSignUp.signUpCase(name, email, password)

        _signUp.value = result


    }

    fun logout() {

        caseLogout.logoutCase()

        _login.value = null

        _signUp.value = null

    }


}