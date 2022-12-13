package com.example.contact_book.domain.usecase.usecaseAuth

import com.example.contact_book.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UseCaseActive @Inject constructor(private val repository: AuthRepository) {

    val currentUserCase: FirebaseUser?
        get() = repository.currentUser


    fun sessionActive(): Boolean {return repository.currentUser != null}


}