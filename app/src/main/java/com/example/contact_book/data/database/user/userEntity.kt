package com.example.contact_book.data.database.user

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "phone") val phone: Int,
    @ColumnInfo(name = "photo") var profilePhoto: Bitmap? = null,
    @Embedded
    val enterprise: Enterprise

    ): Parcelable
    {constructor(id: Int, firstname: String, lastname: String, phone: Int, enterprise: Enterprise) :
            this (id, firstname, lastname,phone,profilePhoto= Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888),enterprise)}

@Parcelize
data class Enterprise(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String

): Parcelable