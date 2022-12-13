package com.example.contact_book.domain.model
import android.app.Activity

class Session(activity: Activity) {

        private val shared: MyShared = MyShared(activity)

        fun saveToken (token: String) {

            shared.putString(token)

        }

        fun isActive (token: String?): Boolean {

            val retriever = shared.getExtraString()

            println("Token of Preferences {$retriever}")

            return retriever == token

        }

        fun close () {

           shared.removePreference()

        }

}