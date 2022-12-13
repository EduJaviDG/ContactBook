package com.example.contact_book.domain.usecase.usecaseToken

import com.example.contact_book.data.repository.TokenRepository
import javax.inject.Inject

class UseCaseDeleteAll @Inject constructor(private val repository: TokenRepository) {


    suspend fun deleteAllCase() = repository.deleteAll()

}