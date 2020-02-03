package com.example.poc.Base

import android.app.Application
import android.util.Log
import com.blockdit.util.language.LanguageManager
import com.example.poc.Utils.SharedPreferenceManager
import com.google.firebase.firestore.FirebaseFirestore

class PocApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        db = FirebaseFirestore.getInstance()
    }


    companion object {
        var db : FirebaseFirestore? = null
    }
}