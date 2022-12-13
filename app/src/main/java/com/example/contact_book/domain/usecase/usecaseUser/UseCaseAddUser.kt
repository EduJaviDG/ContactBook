package com.example.contact_book.domain.usecase.usecaseUser
import com.example.contact_book.data.database.user.User
import com.example.contact_book.data.repository.UserRepository

import javax.inject.Inject

class UseCaseAddUser @Inject constructor(private val repository: UserRepository) {


    suspend fun addCase(user: User) = repository.addUser(user)


}