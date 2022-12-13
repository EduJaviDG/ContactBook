package com.example.contact_book.domain.usecase.usecaseAuth

import com.example.contact_book.data.repository.AuthRepository
import javax.inject.Inject

class UseCaseLogout @Inject constructor(private val repository: AuthRepository) {

    fun logoutCase() = repository.logout()

}