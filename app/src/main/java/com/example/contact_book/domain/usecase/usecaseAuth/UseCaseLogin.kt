package com.example.contact_book.domain.usecase.usecaseAuth

import com.example.contact_book.data.repository.AuthRepository
import javax.inject.Inject

class UseCaseLogin @Inject constructor(private val repository: AuthRepository) {


    suspend fun loginCase(email: String, password: String,) = repository.login(email,password)


}