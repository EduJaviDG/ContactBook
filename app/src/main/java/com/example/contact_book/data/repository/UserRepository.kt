package com.example.contact_book.data.repository

import androidx.lifecycle.LiveData
import com.example.contact_book.data.database.user.User
import com.example.contact_book.data.database.user.UserDao

import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){

        userDao.addUser(user)

    }

    suspend fun updateUser(user: User){

        userDao.updateUser(user)

    }

    suspend fun deleteUser(user: User){

        userDao.deleteUser(user)

    }

    suspend fun deleteAllUsers(){

        userDao.deleteAllUsers()

    }

    fun searchUser(searchQuery: String): LiveData<List<User>> {

        return userDao.searchUser(searchQuery)

    }


}