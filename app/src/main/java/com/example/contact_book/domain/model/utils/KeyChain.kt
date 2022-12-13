package com.example.contact_book.domain.model.utils

enum class KeyChain(private val value:String) {

    INTENT_KEY ("follow_app"),

    SHARED_PREFERENCES_KEY ("token"),

    DEFAULT_KEY ("123456");

   override fun toString() = value
}