package com.example.contact_book.domain.model

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.contact_book.util.KeyChain

class MyShared (activity: Activity): SharedPreferences {

    private val SHARED_KEY = KeyChain.SHARED_PREFERENCES_KEY.toString()

    private val default = KeyChain.DEFAULT_KEY.toString()

    private val shared: SharedPreferences = activity.getSharedPreferences(SHARED_KEY, MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = edit()

    fun putString (value: String?) {

        with(editor){

            editor.putString(SHARED_KEY,value)

            apply()

        }

    }

    fun getExtraString(): String{

        return shared.getString(SHARED_KEY,default).toString()


    }

    fun clearPreference() {

        editor.clear().apply()

    }

    fun removePreference() {

        editor.remove(SHARED_KEY).apply()

    }

    override fun getAll(): MutableMap<String, *> {
        TODO("Not yet implemented")
    }

    override fun getString(key: String?, deafult: String?): String? {

        TODO("Not yet implemented")

    }

    override fun getStringSet(value: String?, p1: MutableSet<String>?): MutableSet<String>? {
        TODO("Not yet implemented")
    }

    override fun getInt(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getLong(p0: String?, p1: Long): Long {
        TODO("Not yet implemented")
    }

    override fun getFloat(p0: String?, p1: Float): Float {
        TODO("Not yet implemented")
    }

    override fun getBoolean(p0: String?, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(key: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(): SharedPreferences.Editor {

        return shared.edit()

    }

    override fun registerOnSharedPreferenceChangeListener(data: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(data: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }
}