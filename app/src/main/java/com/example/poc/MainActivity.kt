package com.example.poc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poc.contactMobile.ContactMainActivity
import com.example.poc.loadImage.TestLoadImageActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var menuItemList: ArrayList<MenuItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        setupView()
    }

    private fun setupView() {
        rcv_menu.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcv_menu.setHasFixedSize(true)
        rcv_menu.adapter = MenuAdapter(
            menuItemList,
            menuClickListener = {
                Intent(this, Class.forName(it.className)).run {
                    startActivity(this)
                }
            }
        )
    }

    private fun initData() {
        menuItemList.add(MenuItem("test contact list", ContactMainActivity::class.java.name))
        menuItemList.add(MenuItem("test load image", TestLoadImageActivity::class.java.name))
    }

    data class MenuItem(val name: String, val className: String)
}
