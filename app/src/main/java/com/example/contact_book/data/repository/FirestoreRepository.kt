package com.example.contact_book.data.repository


interface FirestoreRepository {

    suspend fun adduser(name: String, email: String)


}