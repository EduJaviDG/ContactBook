package com.example.contact_book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_book.domain.usecase.usecaseFirestore.UseCaseAddFiresStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val caseAddFiresStore: UseCaseAddFiresStore):
    ViewModel() {

        fun addUser(name:String, email:String) = viewModelScope.launch {

            caseAddFiresStore.addCase(name, email)

        }


    }