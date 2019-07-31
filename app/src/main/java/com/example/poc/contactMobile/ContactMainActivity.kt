package com.example.poc.contactMobile

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.poc.R
import android.provider.ContactsContract
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.LinearLayout
import com.tbruyelle.rxpermissions2.RxPermissions

const val SELECT_PHONE_NUMBER = 1
class ContactMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_main)
//        setupViewIntentPickup()

        requestPermissionContact()
    }

    @SuppressLint("CheckResult")
    private fun requestPermissionContact() {
        RxPermissions(this)
            .request(Manifest.permission.READ_CONTACTS)
            .subscribe { grant ->
                if (grant) {
                    setupViewIntentPickup()
                }
            }
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
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Profile.DISPLAY_NAME_PRIMARY
            )

            val cursor = contactUri?.let {
                this@ContactMainActivity.contentResolver.query(contactUri, projection, null, null, null)
            }

            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val displayNameIndex = cursor.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME_PRIMARY)

                val number = cursor.getString(numberIndex)
                val displayName = cursor.getString(displayNameIndex)


                addTextView(number, "Number")
                addTextView(displayName, "DisplayName")

            }

            cursor?.close()
        }
    }

    private fun addTextView(textData: String, column: String) {
        val linearLayout = findViewById<View>(R.id.main_layout) as LinearLayout
        val txt = TextView(this@ContactMainActivity)
        txt.text = "$column : $textData"
        linearLayout.addView(txt)
    }
}
