package com.example.poc.switchLanguage

import android.content.Context
import android.text.format.Formatter.formatIpAddress
import android.content.Context.WIFI_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.wifi.WifiManager
import android.text.format.Formatter


object NetworkManager {

    fun getIpAddress(context: Context): String {
        val wm = context.applicationContext.getSystemService( WIFI_SERVICE) as WifiManager?
        val ip = formatIpAddress(wm!!.connectionInfo.ipAddress)
        return ip
    }
}