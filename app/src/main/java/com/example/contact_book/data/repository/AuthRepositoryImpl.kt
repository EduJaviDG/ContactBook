package com.example.contact_book.data.repository

import com.example.contact_book.resource.Resource
import com.example.contact_book.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (private val firebaseAuth: FirebaseAuth): AuthRepository {
    override var currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
        set(value) {}

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {

       return try{

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            Resource.Success(result.user!!)

        } catch (e: Exception){

            Resource.Error(e)

        }

    }

    override suspend fun signUp(name: String, email: String, password: String): Resource<FirebaseUser> {

        return try{

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())

            Resource.Success(result.user!!)

        } catch (e: Exception){

            Resource.Error(e)

        }

    }

    override fun logout() {

    }
}