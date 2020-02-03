package com.example.poc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.poc.Base.BaseActivity
import com.example.poc.Base.Contextor
import com.example.poc.Utils.FirebaseManager
import com.example.poc.Utils.SharedPreferenceManager
import com.example.poc.contactMobile.ContactMainActivity
import com.example.poc.firebase.analyticAndBigquery.TestAnalyticActivity
import com.example.poc.loadImage.TestLoadImageActivity
import com.example.poc.notification.NotificationActivity
import com.example.poc.scanner.ScannerActivity
import com.example.poc.showHideView.ShowHideViewActivity
import com.example.poc.socialView.OverScrollBehaviorActivity
import com.example.poc.switchLanguage.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.util.*
import kotlin.collections.ArrayList

import java.text.SimpleDateFormat





class MainActivity : BaseActivity() {
    var menuItemList: ArrayList<MenuItem> = arrayListOf()
    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    private val sharedPreferences by lazy {
        SharedPreferenceManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number = "+(66)80-844-242 5"
        initData()
        setupView()

        thai_button.setOnClickListener {
//            languageManager?.currentLanguage = LanguageManager.values[0]
//            recreate()
            val washingtonRef = FirebaseManager().fireStore.collection("notice").document("MainTainManage")

            washingtonRef
                .update("isMaintain", true)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }

        eng_button.setOnClickListener {
//            languageManager?.currentLanguage = LanguageManager.values[1]
//            recreate()
            val washingtonRef = FirebaseManager().fireStore.collection("notice").document("MainTainManage")

            washingtonRef
                .update("isMaintain", false)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }

        ss.text = getString(R.string.test_contact_list)

//        setUpRemoteConfig()
//        setUpFireStore()

//        val time1 = "2020-02-01T12:29:04+0700"
//        val time2 = "segfegager"
//        val time3 = "2020-01-28T00:00:00+07:00"
//        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//        val calendar = GregorianCalendar()
//        df.timeZone = TimeZone.getTimeZone("Asia/Bangkok")
//        val currentTime = df.format(calendar.time)
//        testTimezone(time2, time2)

        setupNetworkManager()
    }

    private fun setupNetworkManager() {
        Log.d("tee", "network ip address : " + NetworkManager.getIpAddress(this))
    }

    @SuppressLint("SimpleDateFormat")
    private fun testTimezone(startTime: String, endTime: String) {

        val calendar = GregorianCalendar()
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        df.timeZone = TimeZone.getTimeZone("Asia/Bangkok")
        val currentTime = df.format(calendar.time)

        if (currentTime >= startTime && currentTime <= endTime) {
            Log.d("timezonee", "IN TIME")
        }

        Log.d("timezonee", "current  " + df.format(calendar.time))
        Log.d("timezonee", "startTime  " + startTime)

        Log.d("timezonee", "endTime  $endTime")

    }

    private fun setUpFireStore() {
        // Create a new user with a first, middle, and last name
        val user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

//        FirebaseManager().fireStore.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        FirebaseManager().fireStore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun setUpRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)
        val promotion = mFirebaseRemoteConfig.getBoolean("is_promotion_on")
        val price = mFirebaseRemoteConfig.getLong("price")

        mFirebaseRemoteConfig.fetch().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mFirebaseRemoteConfig.activateFetched()
            }
//            displayPrice()
        }
    }

    fun validateTaxYear(year: Int): Boolean {
        var passed = false
        val currentYear = 2019
        val currentMount = 11
        val currentDay = 11
        val sendDay = 25
        val sendMonth = 1
        if ((year == (currentYear - 1) && currentDay >= sendDay && currentMount >= sendMonth) || year < (currentYear - 1)) {
            passed = true
        }
        return passed
    }

    fun getIpv4(): String? {
        try {
            val en = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf
                    .getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    println("ip1--:$inetAddress")
                    System.out.println("ip2--:" + inetAddress.getHostAddress())

                    if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress().toString()
                    }


                }
            }
        } catch (ex: Exception) {
            Log.e("IP Address", ex.toString())
        }

        return null
    }

    //ipv6
    fun getLocalIpV6(): String? {
        try {
            val en = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf
                    .getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    println("ip1--:$inetAddress")
                    System.out.println("ip2--:" + inetAddress.getHostAddress())

                    if (!inetAddress.isLoopbackAddress() && inetAddress is Inet6Address) {
                        return inetAddress.getHostAddress().toString()
                    }


                }
            }
        } catch (ex: Exception) {
            Log.e("IP Address", ex.toString())
        }

        return null
    }

    private fun setupView() {
        sharedPreferences.save("test reified", "testtt")
        sharedPreferences.getData<String>("testtt")
        rcv_menu.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcv_menu.setHasFixedSize(true)
        rcv_menu.adapter = MenuAdapter(
            menuItemList,
            menuClickListener = {
                Intent(this, Class.forName(it.className)).run {
                    67
                    startActivityForResult(this, 1)
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        thai_button.text = Contextor.getInstance().context!!.getText(R.string.test_scanner)

    }

    private fun initData() {
        menuItemList.add(
            MenuItem(
                getString(R.string.test_contact_list),
                ContactMainActivity::class.java.name
            )
        )
        menuItemList.add(
            MenuItem(
                getString(R.string.test_load_image),
                TestLoadImageActivity::class.java.name
            )
        )
        menuItemList.add(
            MenuItem(
                getString(R.string.test_show_hide_view),
                ShowHideViewActivity::class.java.name
            )
        )
        menuItemList.add(
            MenuItem(
                getString(R.string.test_notification),
                NotificationActivity::class.java.name
            )
        )
        menuItemList.add(
            MenuItem(
                getString(R.string.test_analytic),
                TestAnalyticActivity::class.java.name
            )
        )
        menuItemList.add(MenuItem(test.TEXT.sit, ScannerActivity::class.java.name))
        menuItemList.add(MenuItem("Social page", OverScrollBehaviorActivity::class.java.name))


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    data class MenuItem(val name: String, val className: String)
}
