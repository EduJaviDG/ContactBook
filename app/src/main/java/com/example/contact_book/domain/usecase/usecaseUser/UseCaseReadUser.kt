package com.example.contact_book.domain.usecase.usecaseUser

import com.example.contact_book.data.repository.UserRepository
import javax.inject.Inject

class UseCaseReadUser @Inject constructor(private val repository: UserRepository) {

    fun readCase() = repository.readAllData


}


