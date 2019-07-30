package com.example.poc.contactMobile

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.poc.R
import android.provider.ContactsContract
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


const val SELECT_PHONE_NUMBER = 1
class ContactMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_main)

        setupViewIntentPickup()
    }

    private fun setupViewIntentPickup() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(i, SELECT_PHONE_NUMBER)
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            val contactUri = data?.data
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

            val cursor = contactUri?.let {
                this@ContactMainActivity.contentResolver.query(contactUri, projection, null, null, null)
            }

            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberIndex)
                Log.d("tee", "number $number")
            }

            cursor?.close()
        }
    }
}
