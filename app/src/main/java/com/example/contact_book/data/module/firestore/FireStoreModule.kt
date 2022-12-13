package com.example.contact_book.data.module.firestore

import com.example.contact_book.data.repository.FirestoreRepository
import com.example.contact_book.data.repository.FirestoreRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object FireStoreModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideTokenRepository(impl: FirestoreRepositoryImpl): FirestoreRepository = impl

}