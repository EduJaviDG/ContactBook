package com.example.contact_book.util

enum class pattern (val regex: String) {

    EMAIL_PATTERN ("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),

    PHONE_PATTERN ("^[0-9]{9}\$");

    override fun toString() = regex

}