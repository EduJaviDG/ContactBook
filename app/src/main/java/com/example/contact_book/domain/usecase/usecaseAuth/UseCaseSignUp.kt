package com.example.contact.domain.usecase.usecaseAuth

import com.example.contact_book.data.repository.AuthRepository
import javax.inject.Inject

class UseCaseSignUp @Inject constructor( private val repository: AuthRepository) {


    suspend fun signUpCase(name:String, email: String, password: String) =
        repository.signUp(name, email, password)

}