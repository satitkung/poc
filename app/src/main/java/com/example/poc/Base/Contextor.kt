package com.example.poc.Base

import android.annotation.SuppressLint
import android.content.Context
import java.util.*

/**
 * Created by Wanchalerm Yuphasuk on 4/1/2018 AD.
 */
class Contextor private constructor() {

    var context: Context? = null
    internal fun init(context: Context) {

//        val locale = Locale(lang)
//        val config = context.resources.configuration
//        config.setLocale(locale)
//        context.createConfigurationContext(config)
        this.context = context
    }

    internal fun update(lang: String) {

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Contextor? = null

        fun getInstance(): Contextor {
            if (instance == null)
                instance = Contextor()
            return instance as Contextor
        }
    }

}
