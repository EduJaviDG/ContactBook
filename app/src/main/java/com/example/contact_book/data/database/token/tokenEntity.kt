package com.example.contact_book.data.database.token

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class Token (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "uid") val uid: String,

    @ColumnInfo(name = "id") val id: String)

