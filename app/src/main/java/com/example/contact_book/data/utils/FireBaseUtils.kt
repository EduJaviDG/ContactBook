package com.example.contact_book.data.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {

    return suspendCancellableCoroutine { _continue ->

        addOnCompleteListener { task ->

            if(task.exception != null){

                _continue.resumeWithException( task.exception!!)

            }else{

                _continue.resume(task.result,null)

            }

        }


    }


}