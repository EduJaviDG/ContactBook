package com.example.contact_book.data.database.token

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TokenDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token:Token)

    @Update
    suspend fun updateToken(token: Token)

    @Delete
    suspend fun deleteToken(token: Token)

    @Query("DELETE FROM token_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM token_table")
    fun readToken(): LiveData<List<Token>>

}