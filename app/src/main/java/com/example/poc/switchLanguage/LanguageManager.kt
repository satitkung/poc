package com.blockdit.util.language

import android.content.Context
import android.util.Log
import com.example.poc.R
import com.example.poc.Utils.DEFAULT_LANGUAGE
import com.example.poc.Utils.PREFS_NAME
import java.util.*
import javax.inject.Singleton

@Singleton
class LanguageManager(
    private val context: Context
) {

    var localizedContext: Context = context
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun getStirng(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun saveStirng(key: String) {
        val editor = sharedPreferences.edit()
        editor.putString(DEFAULT_LANGUAGE, key)
        editor.apply()
    }

    var currentLanguage: Language = initCurrentLanguage(getStirng(DEFAULT_LANGUAGE))
        set(value) {

            if (value != field) {
                field = value
                saveStirng(value.code)
                localizedContext = CimbContextWrapper.wrap(context, Locale(field.language, field.country))
            }
        }

    private fun initCurrentLanguage(code: String?): Language {

        val language = code?.let { languageCode ->
            values.firstOrNull { it.code == languageCode }
        } ?: run {
            val defaultLanguage = thai
            saveStirng(defaultLanguage.code)
            defaultLanguage
        }
        localizedContext = CimbContextWrapper.wrap(context, Locale(language.language, language.country))
        return language
    }

    companion object {
        val thai = Language(R.string.language__thai, "th_TH", "th", "TH")
        val eng = Language(R.string.language__eng, "en_EN", "en", "EN")
        val values = arrayOf(thai, eng)
    }

}

data class Language(val languageText: Int, val code: String, val language: String, val country: String)