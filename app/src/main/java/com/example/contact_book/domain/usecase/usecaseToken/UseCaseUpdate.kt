package com.example.contact_book.domain.usecase.usecaseToken

import com.example.contact_book.data.database.token.Token
import com.example.contact_book.data.repository.TokenRepository
import javax.inject.Inject

class UseCaseUpdate @Inject constructor(private val repository: TokenRepository) {

    suspend fun updateCase(token: Token) = repository.updateToken(token)

}