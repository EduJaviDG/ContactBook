package com.example.contact_book.data.repository

import androidx.lifecycle.LiveData
import com.example.contact_book.data.database.token.Token
import com.example.contact_book.data.database.token.TokenDao

import javax.inject.Inject

class TokenRepository @Inject constructor(private val dao: TokenDao) {

    val readToken: LiveData<List<Token>> = dao.readToken()

    suspend fun insertToken(token: Token) = dao.insertToken(token)

    suspend fun updateToken(token: Token) = dao.updateToken(token)

    suspend fun deleteToken(token: Token) = dao.deleteToken(token)

    suspend fun deleteAll() = dao.deleteAll()

}