package com.example.poc.Utils

import android.annotation.SuppressLint
import android.content.Context
import javax.inject.Singleton

const val PREFS_NAME = "POC_TEST"
const val PATH_LOGO_APP = "path_logo_app"
const val DEFAULT_LANGUAGE = "defualt_language"
@SuppressLint("CommitPrefEdits")
@Singleton
class SharedPreferenceManager(val context: Context) {

    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    private val editor by lazy {
        sharedPreferences.edit()
    }

    fun <T> save(data: T, key: String) {

        when (data) {
            is String -> editor.putString(key, data as String)
            is Int -> editor.putInt(key, data as Int)
            is Boolean -> editor.putBoolean(key, data as Boolean)
        }

        editor.commit()
    }

    fun getStirng(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    inline fun <reified T> getData(key: String): T? {
        return when(T::class) {
            String::class -> sharedPreferences.getString(key, "") as T
            Int::class -> sharedPreferences.getInt(key, 0) as T
            Boolean::class -> sharedPreferences.getBoolean(key, false) as T
            else -> sharedPreferences.getString(key, "") as T
        }
//        return sharedPreferences.getString(key, "") as T
    }
}