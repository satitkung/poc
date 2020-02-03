package com.example.poc.scanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.poc.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.navigationbar.*





class ScannerActivity : AppCompatActivity() {

    lateinit var viewpagerAdapter: MainPageViewpagerAdapter
    private val tabTitles = arrayOf("Scan", "Generate QR")
    private val imageResId = intArrayOf(R.drawable.ic_my_qr, R.drawable.ic_my_qr)
    private var oldSelectedTab: TabLayout.Tab? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

//        setupView()
//        setupListener()

    }

    private fun setupListener() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                (oldSelectedTab?.customView as? CustomTabView)?.setTextColor(false)
                oldSelectedTab = tab
                (tab?.customView as? CustomTabView)?.setTextColor(true)
            }

        })
    }

    private fun setupView() {
        setSupportActionBar(toolbars)
        viewpagerAdapter = MainPageViewpagerAdapter(supportFragmentManager, this@ScannerActivity)
        view_pager.adapter = viewpagerAdapter
        tabLayout.setupWithViewPager(view_pager)
        setupIconTab()
    }

    private fun setupIconTab() {

        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView = CustomTabView(this, tabTitles[i], imageResId[i]).apply {
                if (i == 0) {
                    oldSelectedTab = tab
                    setTextColor(true)
                }
            }

        }
    }


}
