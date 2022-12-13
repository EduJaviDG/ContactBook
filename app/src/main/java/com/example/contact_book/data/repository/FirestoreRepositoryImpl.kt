package com.example.contact_book.data.repository

import com.example.contact_book.domain.model.UserFireStore
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore) : FirestoreRepository {
    override suspend fun adduser(
        name: String,
        email: String
    ) {

        val userRegister = UserFireStore (name = name, email = email)

        fireStore.collection("users")
            .document(email).set(userRegister)

    }
}