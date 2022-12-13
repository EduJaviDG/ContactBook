package com.example.contact_book.domain.usecase.usecaseUser

import com.example.contact_book.data.repository.UserRepository
import javax.inject.Inject

class UseCaseSearchUser @Inject constructor(private val repository: UserRepository) {

    fun searchCase(query: String) = repository.searchUser(query)

}