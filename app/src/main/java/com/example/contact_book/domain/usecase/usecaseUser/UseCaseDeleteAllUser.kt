package com.example.contact_book.domain.usecase.usecaseUser

import com.example.contact_book.data.repository.UserRepository
import javax.inject.Inject

class UseCaseDeleteAllUser @Inject constructor(private val repository: UserRepository) {

    suspend fun deleteAll() = repository.deleteAllUsers()
}