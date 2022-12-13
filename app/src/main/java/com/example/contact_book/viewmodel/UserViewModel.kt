package com.example.contact_book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_book.data.database.user.User
import com.example.contact_book.domain.usecase.usecaseUser.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val addUserUser: UseCaseAddUser,
    private val updateUser: UseCaseUpdateUser,
    private val deleteUser: UseCaseDeleteUser,
    private val deleteAllUser: UseCaseDeleteAllUser,
    readUser: UseCaseReadUser,
    private val searchUser: UseCaseSearchUser
) : ViewModel() {

    val readAllData: LiveData<List<User>>

    init {

        readAllData = readUser.readCase()

    }

    fun addUser(user: User) {

        viewModelScope.launch(Dispatchers.IO) {

            addUserUser.addCase(user)

        }

    }

    fun updateUser(user: User) {

        viewModelScope.launch(Dispatchers.IO) {

            updateUser.updateCase(user)

        }

    }

    fun deleteUser(user: User) {

        viewModelScope.launch(Dispatchers.IO) {


            deleteUser.deleteCase(user)

        }

    }

    fun deleteAllUsers() {

        viewModelScope.launch(Dispatchers.IO) {


            deleteAllUser.deleteAll()


        }

    }

    fun searchUser(searchQuery: String): LiveData<List<User>> {


        return searchUser.searchCase(searchQuery)

    }


}



