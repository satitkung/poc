package com.blockdit.util.language

import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.LocaleList
import java.util.*

class CimbContextWrapper(base: Context): ContextWrapper(base) {

    companion object {

        fun wrap(context: Context, locale: Locale): Context {
            var newContext = context
            val res = newContext.resources
            val config = res.configuration

            when {
                SDK_INT >= N -> {
                    config.setLocale(locale)
                    Locale.setDefault(locale)

                    newContext = newContext.createConfigurationContext(config)
                }

                SDK_INT >= LOLLIPOP -> {
                    config.setLocale(locale)

                    newContext = newContext.createConfigurationContext(config)
                }
            }

            return newContext
        }

    }

}