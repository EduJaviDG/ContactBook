package com.example.contatc_book.domain.usecase.usecaseToken

import com.example.contact_book.data.repository.TokenRepository
import javax.inject.Inject

class UseCaseRead @Inject constructor( private val repository: TokenRepository) {

    fun readData() = repository.readToken
}