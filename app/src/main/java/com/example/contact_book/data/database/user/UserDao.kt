package com.example.contact_book.data.database.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query ("SELECT * FROM user_table WHERE firstname LIKE :searchQuery OR lastname LIKE :searchQuery")
    fun searchUser(searchQuery: String): LiveData<List<User>>


}