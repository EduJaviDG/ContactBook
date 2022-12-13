package com.example.contact_book.viewmodel

import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_book.data.database.token.Token
import com.example.contact_book.domain.usecase.usecaseToken.*
import com.example.contatc_book.domain.usecase.usecaseToken.UseCaseRead
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val caseInsert: UseCaseInsert, private val caseUpdate: UseCaseUpdate,
    private val caseDelete: UseCaseDelete, caseRead: UseCaseRead,
    private val caseDeleteAll: UseCaseDeleteAll
) : ViewModel() {

    val readToken: LiveData<List<Token>>

    init {

        readToken = caseRead.readData()

    }

     fun insertToken(token: Token) = viewModelScope.launch {

            caseInsert.insertCase(token)
    }

    fun updateToken(token: Token) = viewModelScope.launch {

        caseUpdate.updateCase(token)

    }

    fun deleteToken(token: Token) = viewModelScope.launch {

        caseDelete.deleteCase(token)

    }

    fun deleteAll() = viewModelScope.launch {

        caseDeleteAll.deleteAllCase()

    }

}