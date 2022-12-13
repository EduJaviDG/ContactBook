package com.example.contact_book.data.database.token

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    private const val DATABASE_NAME = "user_token"

    @Singleton
    @Provides
    fun provideTokenDb (@ApplicationContext context: Context) =
        Room.databaseBuilder( context,
        TokenDataBase::class.java,
        DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideTokenDao(db: TokenDataBase) = db.tokenDao()

}