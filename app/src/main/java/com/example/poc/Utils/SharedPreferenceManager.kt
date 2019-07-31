package com.example.poc.Utils

import android.annotation.SuppressLint
import android.content.Context

const val PREFS_NAME = "POC_TEST"
const val PATH_LOGO_APP = "path_logo_app"
@SuppressLint("CommitPrefEdits")
class SharedPreferenceManager(val context: Context) {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

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
}