package com.example.contact_book.domain.usecase.usecaseUser

import com.example.contact_book.data.database.user.User
import com.example.contact_book.data.repository.UserRepository
import javax.inject.Inject

class UseCaseDeleteUser @Inject constructor(private val repository: UserRepository) {

        suspend fun deleteCase(user: User) = repository.deleteUser(user)


}