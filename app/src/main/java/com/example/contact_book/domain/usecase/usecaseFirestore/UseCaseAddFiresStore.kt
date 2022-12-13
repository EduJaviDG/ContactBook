package com.example.contact_book.domain.usecase.usecaseFirestore

import com.example.contact_book.data.repository.FirestoreRepository
import javax.inject.Inject

class UseCaseAddFiresStore @Inject constructor(private val repository: FirestoreRepository) {

    suspend fun addCase(name: String, email: String){

        repository.adduser(name, email)

    }

}