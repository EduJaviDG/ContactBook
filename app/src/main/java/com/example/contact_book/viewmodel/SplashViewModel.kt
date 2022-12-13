package com.example.contact_book.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private var _isloading = MutableLiveData(true)

    val isloading = _isloading

    init {

        viewModelScope.launch {

            delay(3000)

            _isloading.value = false

        }

    }

}