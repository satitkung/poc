package com.example.poc.contactMobile

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.poc.R
import android.provider.ContactsContract
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_contact_main.*

const val SELECT_PHONE_NUMBER = 1
class ContactMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_main)
//        setupViewIntentPickup()

        requestPermissionContact()
//        requestPermissionContactNormal()
    }

    private fun requestPermissionContactNormal() {
        val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 42)
            }
        } else {

        }
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

                val regex = "[^0-9]".toRegex()
                val digit = number.replace(regex,"")
                if (digit.startsWith("66")) {
                    checkSizeString(digit.replaceFirst("66", "0"))
                } else {
                    checkSizeString(digit)
                }
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

    private fun checkSizeString(number: String) {
        if (number.length > 10) {
            promptpayEditText.setText(number.substring(0, 10))
        } else {
            promptpayEditText.setText(number)
        }
    }

}
