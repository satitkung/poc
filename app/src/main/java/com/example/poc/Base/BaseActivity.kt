package com.example.poc.Base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blockdit.util.language.CimbContextWrapper
import com.blockdit.util.language.LanguageManager
import com.example.poc.Utils.FirebaseManager
import java.util.*

open class BaseActivity : AppCompatActivity() {

     var languageManager: LanguageManager? = null


    override fun attachBaseContext(context: Context) {
        languageManager = LanguageManager(context)


        languageManager?.let {
            super.attachBaseContext(CimbContextWrapper.wrap(context, Locale(it.currentLanguage.language)))
        } ?: run{
            super.attachBaseContext(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val sharedPreferences = SharedPreferenceManager(this)

        Contextor.getInstance().init(this)

        //update real time fireStore
        val docRef = FirebaseManager().fireStore.collection("notice").document("MainTainManage")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "$source data: ${snapshot.data}")
            } else {
                Log.d(TAG, "$source data: null")
            }
        }
    }

    companion object {
        const val TAG = "tee"
    }
}