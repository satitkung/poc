package com.example.poc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.poc.Utils.PATH_LOGO_APP
import com.example.poc.Utils.SharedPreferenceManager
import com.example.poc.Utils.replaceMobileNumberTHCountryCode
import com.example.poc.contactMobile.ContactMainActivity
import com.example.poc.loadImage.TestLoadImageActivity
import com.example.poc.notification.NotificationActivity
import com.example.poc.showHideView.ShowHideViewActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var menuItemList: ArrayList<MenuItem> = arrayListOf()

    private val sharedPreferences by lazy {
        SharedPreferenceManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number = "+(66)80-844-242 5"
        Log.d("TEE", "number: ${number.replaceMobileNumberTHCountryCode()}")
        initData()
        setupView()
    }

    private fun setupView() {
        sharedPreferences.save("test reified", "testtt")
        sharedPreferences.getData<String>("testtt")
        rcv_menu.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcv_menu.setHasFixedSize(true)
        rcv_menu.adapter = MenuAdapter(
            menuItemList,
            menuClickListener = {
                Log.d("TEE", "classname: ${it.className}")
                Intent(this, Class.forName(it.className)).run {
                    startActivity(this)
                }
            }
        )
    }

    private fun initData() {
        menuItemList.add(MenuItem("test contact list", ContactMainActivity::class.java.name))
        menuItemList.add(MenuItem("test load image", TestLoadImageActivity::class.java.name))
        menuItemList.add(MenuItem("test show hide view", ShowHideViewActivity::class.java.name))
        menuItemList.add(MenuItem("test notification", NotificationActivity::class.java.name))

    }

    data class MenuItem(val name: String, val className: String)
}
