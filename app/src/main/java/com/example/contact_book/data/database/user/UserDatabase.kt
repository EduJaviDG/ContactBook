package com.example.contact_book.data.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.contact_book.domain.model.utils.Converters

@Database(entities = [User::class], version = 2)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    /*companion object{

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {

            val tempInstance = INSTANCE

            if(tempInstance != null){

                return tempInstance

            }

            synchronized(this){

                val instance = Room.databaseBuilder(

                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"

                ).build()

                INSTANCE = instance

                return instance

            }

        }


    }*/

}