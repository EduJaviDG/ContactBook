package com.example.contact_book.data.database.token

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Token::class], version = 1)
abstract class TokenDataBase:RoomDatabase() {

    abstract fun tokenDao(): TokenDao

}