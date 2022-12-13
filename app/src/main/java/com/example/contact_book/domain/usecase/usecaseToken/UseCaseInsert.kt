package com.example.contact_book.domain.usecase.usecaseToken

import com.example.contact_book.data.database.token.Token
import com.example.contact_book.data.repository.TokenRepository
import javax.inject.Inject

class UseCaseInsert @Inject constructor(private val repository: TokenRepository) {

    suspend fun insertCase(token: Token) = repository.insertToken(token)



}