package com.example.contact_book.data.repository

import com.example.contact_book.data.resources.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    var currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signUp(name: String, email: String, password: String): Resource<FirebaseUser>

    fun logout()

}